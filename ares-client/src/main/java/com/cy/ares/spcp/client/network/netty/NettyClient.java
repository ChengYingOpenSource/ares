package com.cy.ares.spcp.client.network.netty;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cy.ares.spcp.client.NodeInfo;
import com.cy.ares.spcp.client.Receiver;
import com.cy.ares.spcp.context.SpcpConfig;
import com.google.common.base.Preconditions;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;



public class NettyClient {
    
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);
    
    private SpcpConfig config;
    
    private volatile boolean init = false;
    
    private Bootstrap bootstrap ;
    private EventLoopGroup group;
    private FixedChannelPool pool ;
    
    private NodeInfo node;
    
    private Receiver receiver;
    
    public NettyClient(SpcpConfig config,Receiver receiver){
        this.config = config;
        this.receiver = receiver;
        String serverAddr = this.config.getServerAddr();
        Preconditions.checkArgument(StringUtils.isNotBlank(serverAddr),"serverAddr不能为空!");
    }
    
    public synchronized void init(NodeInfo node) throws Exception {
        if(init) 
            return;
        
        String host = node.getHost();
        int port = node.getPort();
        InetSocketAddress remoteaddress = InetSocketAddress.createUnresolved(host, port);// 连接地址
        group = new NioEventLoopGroup(8);
        bootstrap = new Bootstrap();
        bootstrap.group(group)
         .channel(NioSocketChannel.class)
         .option(ChannelOption.TCP_NODELAY, true)  // 客户端
         .option(ChannelOption.SO_KEEPALIVE, true)  // 客户端
         .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getConnectTimeout())
         .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
         .option(ChannelOption.SO_RCVBUF,32*1024*1000) // 接受buf: 32K
         .remoteAddress(remoteaddress);
         //.handler(new NetClientInitializer());
        // 如果服务端释放 channel, 客户端 channel 应该是 inactive? TODO 验证
        this.pool = new FixedChannelPool(bootstrap, new NetChannelPoolHandler(receiver), this.config.getMaxChannel());
        this.node = node;
        init = true;
    }
    
    
    public Channel getCh(){
        
        Channel channel = null;
        try {
            channel = this.pool.acquire().get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("get channel failed!");
            logger.error(e.getMessage(),e);
        }
        
        return channel;
    }
    
    /**
     * 连接放回连接池,这里一定记得放回去
     * @param channel
     * @return
     */
    public Future<Void> release(Channel channel){
        return this.pool.release(channel);
    }
    
    
    public void close(){
        try {
            logger.info("netty client={} close for channelPool!,NodeInfo={}",this.toString(),JSON.toJSONString(node));
            if(this.pool != null)
                this.pool.close(); // 内部使用到了netty thread 触发 close;
            if(group != null)
                group.shutdownGracefully();
            logger.info("netty client={} close end!,NodeInfo={}",this.toString(),JSON.toJSONString(node));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            logger.error("NettyClient close failed!");
        }
        
    }
    
    
    public SpcpConfig getConfig() {
        return config;
    }
    
}
