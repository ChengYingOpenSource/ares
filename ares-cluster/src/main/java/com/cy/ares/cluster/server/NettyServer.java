package com.cy.ares.cluster.server;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.ares.cluster.ClusterConfig;
import com.cy.ares.cluster.error.ServerBootstarpException;
import com.cy.ares.cluster.eventbus.EventBus;
import com.cy.ares.spcp.message.EventMsg;
import com.cy.cuirass.net.InetUtils;
import com.cy.cuirass.net.InetUtils.HostInfo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * netty server
 * 
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年5月7日 下午2:32:19
 * @version V1.0
 */
public class NettyServer {
    
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
    
    private static final int defThread = 16;
    
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ClusterConfig config;
    
    private volatile boolean close = false;
    
    public NettyServer(ClusterConfig clusterConf) {
        this.config = clusterConf;
    }
    
    public void start(EventBus eventBus) {
        ClusterConfig clusterConf = this.config;
        int maxServerThread = clusterConf.getMaxHandleThread();
        maxServerThread = maxServerThread <= defThread ? defThread : maxServerThread;
        int port = clusterConf.getServerPort();
        boolean flag = false;
        try {
            
            bossGroup = new NioEventLoopGroup(4);
            workerGroup = new NioEventLoopGroup(maxServerThread); // 默认cpu*2
            HostInfo info = InetUtils.getFirstNonLoopbackHostInfo();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(info.getIpAddress(),port);
            logger.info("netty localAddress={}",inetSocketAddress);
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 2048)
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 保持长连接
                    .childOption(ChannelOption.SO_SNDBUF, 32 * 1024 * 1000) // 32k
                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024 * 1000)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .localAddress(inetSocketAddress)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时候触发操作
                        @Override
                        protected void initChannel(SocketChannel sh) throws Exception {
                            sh.pipeline()
                                    .addLast(new IdleStateHandler(0, 0, clusterConf.getIdleTimeoutSecond()))
                                    .addLast(new ProtobufVarint32FrameDecoder())
                                    .addLast(new ProtobufDecoder(EventMsg.Event.getDefaultInstance()))
                                    .addLast(new ProtobufVarint32LengthFieldPrepender())
                                    .addLast(new ProtobufEncoder());
                            // and then business logic.
                            sh.pipeline().addLast(new ServerHandler());
                            sh.attr(EventBus.key).set(eventBus);
                        }
                    });
            
            ChannelFuture future = sb.bind(port).sync();
            
            if (!future.isSuccess()) {
                throw new ServerBootstarpException("netty 服务启动失败！", future.cause());
            }
            logger.info("netty server start success !");
            flag = true;
        } catch (ServerBootstarpException e) {
            throw e;
        } catch (Exception e) {
            flag = false;
            throw new ServerBootstarpException("netty 服务启动失败！", e);
        } finally {
            if (!flag) {
                this.close();
            }
        }
    }
    
    public void close() {
        if (close) {
            return;
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully(); // 关闭线程组
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        close = true;
        
    }
    
}   
    