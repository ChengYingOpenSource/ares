package com.cy.cuirass.raft;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.cuirass.cluster.AkCluster;
import com.cy.cuirass.raft.event.ActionEvent;
import com.cy.cuirass.raft.event.ActionType;
import com.cy.cuirass.raft.event.ElectionTime;
import com.cy.cuirass.raft.event.NodeInitEvent;
import com.cy.cuirass.raft.event.StateChangedEvent;
import com.cy.cuirass.raft.protocol.HtEntryReq;
import com.cy.cuirass.raft.protocol.HtEntryResp;
import com.cy.cuirass.raft.protocol.LogEntryReq;
import com.cy.cuirass.raft.protocol.SeekReq;
import com.cy.cuirass.raft.protocol.SeekResp;
import com.cy.cuirass.raft.protocol.VoteReq;
import com.cy.cuirass.raft.protocol.VoteResp;
import com.cy.cuirass.raft.state.NodeState;
import com.cy.cuirass.raft.state.StateClock;
import com.cy.onepush.dcommon.event.MEvent;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.CurrentClusterState;
import akka.cluster.ClusterEvent.LeaderChanged;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;


@SuppressWarnings("all")
public class NodeActor extends AbstractActor{
    
    private static final Logger logger = LoggerFactory.getLogger(NodeActor.class);
    
    private NodeState state;
    
    private StateClock clock ;
    
    private long term = 0;
    
    // 每个term 周期 vote context, candidate阶段使用
    private VoteContext voteCtx;
    private AkCluster akCluster;
    
    private RaftLeaderListener leaderListener;
    
    private String raftTopic = "raft-topic";
    
    public NodeActor(AkCluster akCluster,RaftLeaderListener leaderListener){
        this.state = NodeState.follower;
        this.clock = new StateClock(this.state,ElectionTime.election(),candidateCallback,false);
        this.akCluster = akCluster;
        this.leaderListener = leaderListener;
        
        ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();
        mediator.tell(new DistributedPubSubMediator.Subscribe(raftTopic, getSelf()),getSelf());
    }
    
    @Override
    public void preStart() {
        this.akCluster.getCluster().subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), MemberEvent.class, UnreachableMember.class,MemberRemoved.class,MemberUp.class);
        this.akCluster.getCluster().subscribe(getSelf(), LeaderChanged.class);
    }
    
    
    private Callable<Boolean> candidateCallback = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            getSelf().tell(new StateChangedEvent(NodeState.candidate), getSelf());
            return true;
        }
    };
    
    
    private Callable<Boolean> heartCallback = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            getSelf().tell(new ActionEvent(ActionType.pub_htEntry), getSelf());
            return true;
        }
    };
    
    private Callable<Boolean> voteCallback = new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            getSelf().tell(new ActionEvent(ActionType.ac_voteCompute), getSelf());
            return true;
        }
    };
    
    @Override
    public Receive createReceive() {
        
        return receiveBuilder().match(MemberUp.class, mUp -> {
            //Nothing
        }).match(UnreachableMember.class, mUnreachable -> {
            //Nothing
        }).match(MemberRemoved.class, mRemoved -> {
           //Nothing
        }).match(ActionEvent.class, msg -> {
            actionHandle(msg);
        }).match(VoteReq.class, msg -> {
            long outTerm = msg.getTerm();
            if(this.term < outTerm){
                this.term = outTerm;
                toFllower();
                getSender().tell(new VoteResp(this.term,true), getSelf());
            }else{
                getSender().tell(new VoteResp(this.term,false), getSelf());
            }
        }).match(VoteResp.class, msg -> {
            if(getSelf() == getSender()){
                return;
            }
            voteHandle(msg);
        }).match(SeekReq.class, msg -> {  // seek 是否多余 ? 
            if(this.state == NodeState.leader){
                getSender().tell(new SeekResp(this.term), getSelf());
            }
        }).match(SeekResp.class, msg -> {
            if(this.state == NodeState.follower){
                this.term = msg.getTerm();
            }
        }).match(LogEntryReq.class, msg -> {
            // 暂无
        }).match(NodeInitEvent.class, msg->{ // note : initEvent 并非第一个接收到的事件
            getSelf().tell(new ActionEvent(ActionType.pub_seekReq), getSelf());
            this.clock.start();
        }).match(StateChangedEvent.class, msg->{
            NodeState will = msg.getWill();
            if(will == NodeState.candidate){
                toCandidate();
            }else if(will == NodeState.follower){
                toFllower();
            }else if(will == NodeState.leader){
                toLeader();
            }
        }).match(HtEntryReq.class, msg-> {
            this.akCluster.setMasterNode(msg.getNode());
            if(getSelf() == getSender()){
                return;
            }
            logger.debug("heartbeat req entry received!");
            long outTerm = msg.getTerm();
            if(outTerm >= this.term){
                this.term = outTerm;
                // 暂时不解决网络分区问题
                toFllower();
            }
            getSender().tell(new HtEntryResp(this.term), getSelf());
        }).match(HtEntryResp.class, msg-> {
            if(this.state == NodeState.follower){
                this.clock.reset();
            }
        }).build();
    }
    
    
    public void actionHandle(ActionEvent event) {
        
        ActionType type = event.getType();
        if(type == ActionType.pub_htEntry){
            publish(new HtEntryReq(this.term,this.akCluster.getLocalNode()));
        }else if(type == ActionType.pub_seekReq){
            publish(new SeekReq());
        }else if(type == ActionType.ac_voteCompute){
            voteCompute(true);
        }
    }
    
    
    public void toFllower(){
        
        logger.debug("toFllower cur nodeState={}",this.state);
        if(this.state == NodeState.follower){
            this.clock.reset();
        }else if(this.state == NodeState.candidate){
            this.clock.stop();
            this.state = NodeState.follower;
            this.clock = new StateClock(this.state,ElectionTime.election(),candidateCallback,false);
            this.clock.start();
        }else if(this.state == NodeState.leader){
            this.clock.stop();
            this.state = NodeState.follower;
            this.clock = new StateClock(this.state,ElectionTime.election(),candidateCallback,false);
            this.clock.start();
            if(this.leaderListener != null)
                this.leaderListener.electionLeave();
        }
    }
    
    public void toCandidate(){
        logger.debug("toCandidate cur nodeState={}",this.state);
        // 只有 follower -> candidate
        this.state = NodeState.candidate;
        this.clock.stop();
        this.clock = new StateClock(this.state,ElectionTime.votetime(),voteCallback,false);
        // 开始 vote;
        startVote();
        this.akCluster.setMasterNode(null);
        this.clock.start();
    }
    
    public void startVote(){
        this.term ++ ;
        this.voteCtx = new VoteContext();
        this.voteCtx.addCount(1);
        this.voteCtx.setTerm(this.term);
        
        publish(new VoteReq(this.term));
    }
    
    public void voteCompute(boolean isClockTrigger){
        if(this.state != NodeState.candidate || this.voteCtx == null){
            return;
        }
        CurrentClusterState clusterState =  this.akCluster.getCluster().state();
        int memberNunm = clusterState.members().size();
        
        if(this.voteCtx.getCount() > memberNunm/2 && memberNunm>=1){
            // to leader;
            logger.debug("voteCompute will progress ,isClock={} count={},allNumNode={},term={}",isClockTrigger,this.voteCtx.getCount(),memberNunm,this.term);
            toLeader();
        }else{
            logger.debug("voteCompute not progress ,isClock={} count={},allNumNode={},term={}",isClockTrigger,this.voteCtx.getCount(),memberNunm,this.term);
            if(isClockTrigger){
                // 时间到，选举失败，重新为fllower;?? 
                // 重新成为 candidate
                toCandidate();
            }
        }
        
    }
    
    // 计算投票时间点：投票时间耗尽为止
    public void voteHandle(VoteResp voteResp){
        
        // 可能会获取到旧的message resp?? 通过term判断
        // 未获取vote || 状态不是candidate || 不在同一个term
        if(!voteResp.isVote() || this.state != NodeState.candidate || voteResp.getFromTerm() != this.term || this.voteCtx == null){
            logger.debug("voteResp handle is failed!,voteResult={}",voteResp.isVote());
            return; 
        }
        // 判断 term; 
        // 可以有多个 发起vote的; 
        this.voteCtx.addCount(1);
        voteCompute(false);
        
    }
    
    
    public void toLeader(){
        
        logger.info("toLeader cur nodeState={}",this.state);
        
        this.state = NodeState.leader;
        this.clock.stop();
        this.clock = new StateClock(this.state,ElectionTime.hearttime,heartCallback,true);
        this.clock.start();
        this.voteCtx = null;
        if(this.leaderListener != null)
            this.leaderListener.electionUp();
    }
    
    
    
    public void publish(MEvent msg){
        msg.setNamespace(raftTopic);
        this.akCluster.publish(msg, getSelf());
    }
    
    
    
    /**
     * 
     */
    public void 说明(){
                
        //1 . pub , mevent who is leader
        //2 . pub谁是leader ; 新建clock 超时则没有 ; 有则, clock stop, 获取最新的 term; 直到leader member removed!  重复1
        //    接受到 append entry(即心跳), 也说明是leader; 但是在akka下面应该没有这个消息
        //    接受到投票信息; 投票并且reset clock;
        
        //3.  to candidate
        //4.  send vote 信息; 自己vote自己;
        //5.  接受到的投票信息没有超过一半 ; term再增加,再投一次;
        //6.  接受其它的投票信息, 如果term> 自己的, state -> follwer, 进行投票
        
        //4. to leader
        //5. 定时发送心跳,告知 who is leader;
        //6. 如果有更高term 的leader, 即之前有网络分区, state -> follwer. 
        
        
    }
    
}
