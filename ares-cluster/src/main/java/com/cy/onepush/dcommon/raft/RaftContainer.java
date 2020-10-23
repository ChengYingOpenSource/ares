package com.cy.onepush.dcommon.raft;
import java.util.List;

import com.cy.onepush.dcommon.event.MEvent;
import com.cy.onepush.dcommon.raft.transport.Receiver;
import com.cy.onepush.dcommon.raft.transport.Sender;

import lombok.Getter;


/**
 * raft 容器:
 *   election
 *   replication
 *   
 * 
 * 并且主要和外部进行API交互
 *   
 * @author maoxq
 *
 * @Description 
 *
 * @date 2018年5月25日 下午4:54:42
 * @version V1.0
 */
@Getter
public class RaftContainer {
    
    
    private RaftConfig config ;
    
    private RFNode rfNode;
    
    private RFCluster rfCluster;
    
    private Sender sender ;
    
    private Receiver receiver;
    
    public RaftContainer(RaftConfig config){
        
        this.config = config;
        rfNode = new RFNode();
        rfNode.setState(StateEnum.init);
        
        rfCluster = new RFCluster();
        rfCluster.setClusterNode(config.getNetNodes());
        
        sender = new Sender(this);
        
        rfNode.setRaftContainer(this);
        rfNode.start();
    }
    
    // 上层的时间通知
    public void emitter(MEvent mevent){
        
        // TODO 区分事件
        
        
    }
    
    /**
     * eureka更新节点之后
     * @param netNodes
     * @return
     */
    public RFCluster updateCluster(List<NetNode> netNodes){
        
        //1.updateCluster
        //2.判断master是不是已经移除了
        //(心跳没有了也认为master停止了)
        
        return null;
    }
    
    public void updateLeader(NetNode leader){
        this.rfCluster.setLeader(leader);
    }
    
    
    public void destory(){
        
        
        this.rfNode.stop();
        
    }
    
}




