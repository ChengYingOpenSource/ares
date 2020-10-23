package com.cy.cuirass.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cy.cuirass.cluster.asyn.HolderCallable;
import com.cy.cuirass.cluster.asyn.HolderCounterFuture;
import com.cy.cuirass.cluster.asyn.HolderFuture;
import com.cy.cuirass.cluster.event.DestAckMEvent;
import com.cy.cuirass.cluster.event.DestMEvent;
import com.cy.cuirass.cluster.event.PubAckMEvent;
import com.cy.cuirass.cluster.event.PubMEvent;
import com.cy.cuirass.raft.NodeActor;
import com.cy.cuirass.raft.RaftLeaderListener;
import com.cy.cuirass.raft.event.NodeInitEvent;
import com.cy.onepush.dcommon.async.Async;
import com.cy.onepush.dcommon.event.LocalSubscriberRegistry;
import com.cy.onepush.dcommon.event.MEvent;
import com.cy.onepush.dcommon.event.Subscriber;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.CurrentClusterState;

public class AkCluster {

    private static final Logger logger = LoggerFactory.getLogger(AkCluster.class);

    // load 配置
    private ActorSystem system; // cluster system , 不要用于业务处理
    private Cluster cluster;
    private String clusterSysName;

    private ActorRef listener ;
    private volatile boolean init = false;
    private volatile boolean close = false;
    
    private volatile boolean pubSub = false;

    private ActorRef publisher;
    private ActorRef subscriber;

    private ActorRef dest;
    private ActorRef uniqueDest;
    private ActorRef sender;
    
    private LocalSubscriberRegistry registry ;
    private Async async ;
    
    private ActorRef raftNode ;
    private ClusterListener clusterListener;
    private AkNode localNode;
    
    private AkNode masterNode;
    
    public synchronized void init(String clusterSysName, AkNode akNode,Async async,ClusterListener listener,List<AkNode> asNodes) {
        if (init)
            return;
        logger.info("AkCluster={} init started!",clusterSysName);
        this.localNode = akNode;
        int port = akNode.getPort();
        String address = akNode.linkAddress();
        // Override the configuration
        // 覆盖 hostname, akka uuid: hostname:port:uid ,不同环境 hostname 会获取不正确
        Config config = ConfigFactory.parseString(
                "silvery.akka.remote.netty.tcp.hostname=\"" + address + "\" \n" + 
                "silvery.akka.remote.netty.tcp.bind-hostname=\"" + address + "\" \n" + 
                "silvery.akka.remote.netty.tcp.public-hostname=\"" + address + "\" \n" + 
                "silvery.akka.remote.netty.tcp.port=" + port + "\n" + 
                "silvery.akka.remote.netty.tcp.ip=" + address).withFallback(ConfigFactory.load());
        
        // Create an Akka system
        this.system = ActorSystem.create(clusterSysName, config.getConfig("silvery"));
        this.cluster = Cluster.get(system);
        this.clusterSysName = clusterSysName;
        this.registry = new LocalSubscriberRegistry();
        this.async = async;
        this.clusterListener = listener;
        logger.info("AkCluster={} init finished!",clusterSysName);
        join(asNodes);
        init = true;
    }
    
    private synchronized void join(List<AkNode> asNodes) {
        logger.info("join seed nodes={}",JSON.toJSONString(asNodes));
        List<Address> addresses = new ArrayList<>();
        for (AkNode akNode : asNodes) {
            Address address = Address.apply("akka.tcp", this.clusterSysName, akNode.linkAddress(), akNode.getPort());
            addresses.add(address);
        }
        cluster.joinSeedNodes(addresses);
        pubSub();
    }
    
    private void pubSub(){
        if (pubSub)
            return;
        subscriber = this.system.actorOf(Props.create(AkSubscriber.class,this,this.async,this.registry,Subscriber.Default), "subscriber");
        publisher = this.system.actorOf(Props.create(AkPublisher.class,this), "publisher");
        
        dest = this.system.actorOf(Props.create(AkDestination.class,this,this.async,this.registry), "destination");
        sender = system.actorOf(Props.create(AkSender.class,this), "sender");
        
        DestMEvent tt1 = new DestMEvent(this.localNode,new MEvent());
        tt1.setClusterName(this.clusterSysName);
        uniqueDest = this.system.actorOf(Props.create(AkDestination.class,this,this.async,this.registry), "destination-unique"+tt1.addressHashCode());
        
        this.listener = this.system.actorOf(Props.create(AkClusterListener.class,clusterListener), "listener"); 
        pubSub = true;
    }
    
    
    public synchronized void close(){
        if(close)
            return;
        logger.info("cluster trigger to closed!");
        this.system.terminate();
        this.cluster.shutdown();
        // TODO Async close方法;
        close = true;
    }
    
    public void publish(MEvent message) {
        if(close){
            logger.warn("cluster has closed!");
            return;
        }
        // to cluser
        try {
            publisher.tell(message, null);
        } catch (Exception e) {
           logger.error("publish error!,e={}",e);
        }
    }
    
    public Future<Object> publish(PubMEvent message) {
        if(close){
            logger.warn("cluster has closed!");
            return null;
        }
        // to cluser
        try {
            String uuid = message.getUuid();
            if(StringUtils.isBlank(uuid)){
                message.setUuid(UUID.randomUUID().toString());
            }
            publisher.tell(message, null);
            if (message.isShouldAck()) {
                CurrentClusterState css = cluster.state();
                int size = css.members().size();
                AtomicInteger countAck = new AtomicInteger(size);
                Callable<Object> callable = (new Callable<Object>(){
                    @Override
                    public Object call() throws Exception {
                        return "OK";
                    }
                });
                HolderCallable<Object> hc = new HolderCallable<Object>(callable);
                HolderCounterFuture<Object> f = new HolderCounterFuture<>(hc,countAck);
                logger.info("publish Ack count={},uuid={}",countAck,uuid);
                ackMap.put(uuid, f);
                return f;
            }
            return null;
        } catch (Exception e) {
           logger.error("publish error!,e={}",e);
        }
        return null;
    }

    public void send(MEvent message) {
        if(close){
            logger.warn("cluster has closed!");
            return;
        }
        try {
            sender.tell(message, null);
        } catch (Exception e) {
           logger.error("send error!,e={}",e);
        }
    }
    
    // 默认60s消失; 无线大cache;
    private Cache<String,  FutureTask<Object>> ackMap =  CacheBuilder.newBuilder().maximumSize(Long.MAX_VALUE).expireAfterWrite(30, TimeUnit.SECONDS).build();
    
    public Future<Object> sendWithAck(MEvent message) {
        if(close){
            logger.warn("cluster has closed!");
            return null;
        }
        Callable<Object> callable = (new Callable<Object>(){
            @Override
            public Object call() throws Exception {
                return "OK";
            }
        });
        
        return send(message,callable);
    }
    
    // TODO 修改为 onComplete | callback 
    public Future<Object> send(boolean isMaster,MEvent message) throws IllegalStateException{
        Callable<Object> callable = (new Callable<Object>(){
            @Override
            public Object call() throws Exception {
                return "OK";
            }
        });
        
        return send(isMaster,message,callable);
    }
    
    public Future<Object> send(boolean isMaster,MEvent message,Callable<Object> callable) throws IllegalStateException{
        
        if(isMaster){
            if(this.masterNode == null){
                throw new IllegalStateException("当前master 节点不存在！");
            }
            DestMEvent t = new DestMEvent(message);
            t.setDestAddress(this.masterNode.linkAddress());
            t.setDestPort(this.masterNode.getPort());
            message = t;
        }
        
        return send(message,callable);
        
    }
    
    // callable 起到的是 callback的作用，不会返回什么值 ; 
    public Future<Object> send(MEvent message,Callable<Object> callable) {
        if(close){
            logger.warn("cluster has closed!");
            return null;
        }
        HolderCallable<Object> hc = new HolderCallable<Object>(callable);
        HolderFuture<Object> f = new HolderFuture<>(hc);
        try {
            String uuid = null;
            if(message instanceof DestMEvent){
                DestMEvent t = ((DestMEvent)message);
                t.setClusterName(this.clusterSysName);
                uuid = t.getUuid();
                if(StringUtils.isBlank(uuid)){
                    uuid = UUID.randomUUID().toString();
                    t.setUuid(uuid);
                }
                sender.tell(message, null);
            }else{
                DestMEvent t = new DestMEvent(message);
                t.setClusterName(this.clusterSysName);
                uuid = t.getUuid();
                if(StringUtils.isBlank(uuid)){
                    uuid = UUID.randomUUID().toString();
                    t.setUuid(uuid);
                }
                sender.tell(t, null);
            }
            
            ackMap.put(uuid, f);
        } catch (Exception e) {
           logger.error("send error!,e={}",e);
           f.cancel(true);
        }
        return f;
        
    }
    
    public void ackEvent(MEvent msg) {
        
        logger.info("receive ack event,msgClass={}",msg.getClass().getSimpleName());
        if(msg instanceof DestAckMEvent){
            DestAckMEvent message = (DestAckMEvent)msg;
            String uuid = message.getUuid();
            HolderFuture<Object> f = (HolderFuture<Object>)ackMap.getIfPresent(uuid);
            if(f == null)
                return;
            try {
                ackMap.invalidate(uuid);
                if(f.getHcCallable() != null){
                    f.getHcCallable().setResult(message);
                }
                f.run();
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        }else if (msg instanceof PubAckMEvent){
            PubAckMEvent message = (PubAckMEvent)msg;
            String uuid = message.getUuid();
            HolderCounterFuture<Object> f = (HolderCounterFuture<Object>)ackMap.getIfPresent(uuid);
            if(f == null){
                logger.warn("receive PubAckMEvent future holder not exist!,uuid={}",uuid);
                return;
            }
            try {
                boolean flag = f.decrement();
                if(flag){
                    ackMap.invalidate(uuid);
                    if(f.getHcCallable() != null){
                        f.getHcCallable().setResult(message);
                    }
                    f.run();
                    logger.info("receive PubAckMEvent future is trigger!uuid={}",uuid);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        }
    }  
    
    public void publish(MEvent message,ActorRef sender) {
        // to cluser
        if(close){
            logger.warn("cluster has closed!");
            return;
        }
        try {
            publisher.tell(message, sender);
        } catch (Exception e) {
           logger.error("publish with sender error!,e={}",e);
        }
    }

    public void send(MEvent message,ActorRef sender) {
        if(close){
            logger.warn("cluster has closed!");
            return;
        }
        try {
            sender.tell(message, sender);
        } catch (Exception e) {
           logger.error("send with sender error!,e={}",e);
        }
    }
    
    public int clusterNum(){
        CurrentClusterState clusterState =  this.cluster.state();
        return clusterState.members().size();
    }
    
    
    public synchronized void raftNode(RaftLeaderListener listener) {
        raftNode = this.system.actorOf(Props.create(NodeActor.class,this,listener), "raftNode");
        logger.info("cluster create raft actor with leader listener!");
    }
    
    public synchronized void raftInit(){
        logger.info("start to raftInit!");
        raftNode.tell(new NodeInitEvent(),null);
    }
    
    public LocalSubscriberRegistry getRegistry() {
        return registry;
    }
    
    public ActorSystem getSystem() {
        return system;
    }
    
    public Cluster getCluster() {
        return cluster;
    }
    
    public AkNode getLocalNode() {
        return localNode;
    }
    
    public AkNode getMasterNode() {
        return masterNode;
    }
    
    public AkCluster setMasterNode(AkNode masterNode) {
        this.masterNode = masterNode;
        return this;
    }

    public String getClusterSysName() {
        return clusterSysName;
    }
    
    
}
