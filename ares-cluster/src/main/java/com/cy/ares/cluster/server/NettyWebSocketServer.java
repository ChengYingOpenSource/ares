package com.cy.ares.cluster.server;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.ares.cluster.ClusterConfig;
import com.cy.ares.cluster.error.ServerBootstarpException;
import com.cy.ares.cluster.eventbus.EventBus;
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
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;



public class NettyWebSocketServer {
    
    private static final Logger logger = LoggerFactory.getLogger(NettyWebSocketServer.class);
    
    public static final AttributeKey<String> wsKey = AttributeKey.newInstance(NettyWebSocketServer.class.getSimpleName());
    
    private static final int defThread = 16;
    
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ClusterConfig config;
    
    private volatile boolean close = false;
    
    public NettyWebSocketServer(ClusterConfig clusterConf) {
        this.config = clusterConf;
    }
    
    public void start(EventBus eventBus) {
        ClusterConfig clusterConf = this.config;
        int maxServerThread = clusterConf.getMaxHandleThread();
        maxServerThread = maxServerThread <= defThread ? defThread : maxServerThread;
        int port = clusterConf.getWebSocketPort();
        boolean flag = false;
        try {

            bossGroup = new NioEventLoopGroup(4);
            workerGroup = new NioEventLoopGroup(maxServerThread); // 默认cpu*2
            HostInfo info = InetUtils.getFirstNonLoopbackHostInfo();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(info.getIpAddress(),port);
            logger.info("netty webSocket localAddress={}",inetSocketAddress);
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
                                    .addLast(new HttpServerCodec())
                                    .addLast(new ChunkedWriteHandler())
                                    .addLast(new HttpObjectAggregator(8192))
                                    .addLast(new WebSocketServerProtocolHandler("/ares",null,true,65535));
                            // and then business logic.
                            sh.pipeline().addLast(new WebSocketIndexPageHandler("/ares"));
                            sh.pipeline().addLast(new EventToTextWsFrameEncoder());
                            sh.pipeline().addLast(new TextWebSocketFrameHandler());
                            sh.attr(EventBus.key).set(eventBus);
                            sh.attr(wsKey).set(NettyWebSocketServer.class.getSimpleName());
                        }
                    });
            
            ChannelFuture future = sb.bind(port).sync();
            
            if (!future.isSuccess()) {
                throw new ServerBootstarpException("netty web socket 服务启动失败！", future.cause());
            }
            logger.info("netty web socket start success !");
            flag = true;
        } catch (ServerBootstarpException e) {
            throw e;
        } catch (Exception e) {
            flag = false;
            throw new ServerBootstarpException("netty web socket 服务启动失败！", e);
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
