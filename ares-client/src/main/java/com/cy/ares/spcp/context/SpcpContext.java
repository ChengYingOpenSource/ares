package com.cy.ares.spcp.context;

import com.cy.ares.spcp.actor.cache.CacheCompareActor;
import com.cy.ares.spcp.actor.cache.DataConfCache;
import com.cy.ares.spcp.actor.timer.TimerCacheCompare;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.ares.spcp.actor.RespActor;
import com.cy.ares.spcp.actor.fetcher.ConfigFetchActor;
import com.cy.ares.spcp.actor.fetcher.PushCallback;
import com.cy.ares.spcp.actor.register.ServiceRegisteActor;
import com.cy.ares.spcp.actor.timer.TimerHeartbeatCheck;
import com.cy.ares.spcp.client.Receiver;
import com.cy.ares.spcp.client.Sender;
import com.cy.ares.spcp.client.network.netty.NettyPoolManager;
import com.cy.ares.spcp.message.MessageConvert;
import com.cy.ares.spcp.net.protocol.ProtocolCenter;
import com.cy.ares.spcp.net.protocol.ProtocolCenter.Resp;
import com.cy.ares.spcp.protocol.ClientInstanceInfo;
import com.cy.ares.spcp.service.ClientServiceInfo;
import com.cy.ares.spcp.protocol.ProtocolRegister;

public class SpcpContext implements RefreshContext {

    private static final Logger logger = LoggerFactory.getLogger(SpcpContext.class);

    // ms
    private final static int default_readTimeout = 10000;
    private final static int default_connectTimeout = 3000;

    private SpcpConfig config;
    private volatile boolean shutdownHookAdded = false;
    private volatile boolean close = false;
    private volatile boolean start = false;
    private volatile boolean init = false;
    private RespActor respActor;
    private ServiceRegisteActor serviceRegisteActor;
    private Receiver receiver;
    private Sender sender;
    private ClientInstanceInfo instanceInfo;

    /***
     * heartbeat checker
     */
    private TimerHeartbeatCheck hbcheck;
    private TimerCacheCompare cacheCompare;

    // actor
    private ConfigFetchActor confFetchActor;
    private CacheCompareActor cacheCompareActor;

    public SpcpContext() {

    }

    public SpcpContext(SpcpConfig conf, ClientInstanceInfo instanceInfo) {
        init(conf,instanceInfo);
    }

    public synchronized void init(SpcpConfig conf, ClientInstanceInfo instanceInfo) {
        if(init) {
            return;
        }
        this.config = check(conf);
        this.instanceInfo = instanceInfo;
        MessageConvert.setDefInstanceId(instanceInfo.getInstanceId());
        ProtocolRegister.registe();
        ProtocolCenter.registe(Resp.conf_push, new PushCallback());

        DataConfCache cache = new DataConfCache();
        if(confFetchActor == null) {
            confFetchActor = new ConfigFetchActor(this, cache);
        }

        cacheCompareActor = new CacheCompareActor(this, cache);
        init = true;
    }
    public synchronized boolean bootstrap() {
        try {
            if (start) {
                return start;
            }
            bootstrap0();
            start = true;
            close = false;
            logger.info("ares2 start with config = [{}]", this.config);
            return start;
        } catch (Exception e) {
            start = false;
            logger.error("SpcpContext Exception,will close()!");
            close();
            throw e;
        }
    }

    public void respActor(RespActor respActor) {
        this.respActor = respActor;
    }

    public void serviceRegisteActor(ServiceRegisteActor serviceRegisteActor) {
        this.serviceRegisteActor = serviceRegisteActor;
    }

    public void receiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public void sender(Sender sender) {
        this.sender = sender;
    }

    public void timerHeartbeatCheck(TimerHeartbeatCheck timerHeartbeatCheck) {
        this.hbcheck = timerHeartbeatCheck;
    }

    public void timerCacheCompare(TimerCacheCompare timerCacheCompare) {
        this.cacheCompare = timerCacheCompare;
    }

    public void configFetchActor(ConfigFetchActor confFetchActor) {
        this.confFetchActor = confFetchActor;
    }

    private ConfigFetchActor bootstrap0() {
        addShutdownHook();

        // resp actor
        this.respActor = new RespActor(this);
        // Sender & Receiver 初始化
        receiver = new Receiver(this);
        // 通信层 初始化
        NettyPoolManager npl = new NettyPoolManager(this.config, receiver);
        npl.init();
        if(sender == null) {
            sender = new Sender(npl, receiver);
        }
        // 连接 channel check; TODO?

        // actor 功能初始化
        // 1. 注册服务信息
        if(serviceRegisteActor == null) {
            serviceRegisteActor = new ServiceRegisteActor(this, this.instanceInfo);
        }
        // TODO 服务注册，服务器返回 服务端的 cluster 信息, 如果当前服务端 负载过重， 则  NettyPoolManager refresh 到指定 server ;
        // 服务端信息 确实需要不断的刷新 ，仅仅靠这次的刷新是不够的; =>  serviceRegisteActor 添加刷新机制; 
        boolean f = serviceRegisteActor.registe(new ClientServiceInfo());
        if (!f) {
            this.close();
            throw new BootstrapException("service registe failed!");
        }
        // 2. 心跳开始
        if(hbcheck == null) {
            hbcheck = new TimerHeartbeatCheck(this);
        }
        hbcheck.start();

        // 3. 第一次获取 & 注册配置信息 ;
        f = confFetchActor.fetch(false, this.config.getInitReadTimeout());
        if (!f) {
            logger.warn("fetch config failed!");
            this.close();
            throw new BootstrapException("fetch config to init failed!");
        }
        cacheCompare = new TimerCacheCompare(this);
        cacheCompare.start();
        return confFetchActor;
    }

    // TODO NettyPoolManager refresh 到指定 server, 服务端信息 确实需要不断的刷新 , 
    @Override
    public void refreshServer() {
        if (close) {
            return;
        }
    }

    /**
     * 被心跳所调用, 每次 network refresh之后，进行 refreshConfig;
     */
    @Override
    public synchronized void refreshConfig() {
        if (close) {
            return;
        }
        boolean f = serviceRegisteActor.registe(new ClientServiceInfo());
        f = confFetchActor.fetch(true);
        if (!f) {
            logger.warn("refreshServer fetch config failed!");
        }
    }

    @Override
    public synchronized void close() {
        if (close) {
            return;
        }
        logger.info("context start to close!");
        if (this.sender != null) {
            this.sender.close();
        }
        if (this.receiver != null) {
            this.receiver.close();
        }
        if (this.respActor != null) {
            this.respActor.close();
        }
        if (this.hbcheck != null) {
            this.hbcheck.stop();
        }
        if (this.cacheCompare != null) {
            this.cacheCompare.stop();
        }
        close = true;
        start = false;
    }

    private SpcpConfig check(SpcpConfig conf) {

        SpcpConfig confValid = new SpcpConfig();
        String serverAddr = conf.getServerAddr();
        if (StringUtils.isBlank(serverAddr)) {
            throw new IllegalArgumentException("serverAddr不能为空!");
        }
        int readTimeout = conf.getReadTimeout();
        readTimeout = readTimeout <= 0 ? default_readTimeout : readTimeout;
        int connectTimeout = conf.getConnectTimeout();
        connectTimeout = connectTimeout <= 0 ? default_connectTimeout : connectTimeout;

        confValid.setServerAddr(serverAddr);
        confValid.setReadTimeout(readTimeout);
        confValid.setConnectTimeout(connectTimeout);
        confValid.setInitReadTimeout(conf.getInitReadTimeout());

        return confValid;
    }

    public synchronized void addShutdownHook() {
        if (shutdownHookAdded) {
            return;
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.info("[Ares2Client] Trigger ShutdownHook success.");
                close();
            }
        });
        logger.info("[Ares2Client] addShutdownHook success.");
        shutdownHookAdded = true;
    }

    public RespActor getRespActor() {
        return respActor;
    }

    public SpcpConfig getConfig() {
        return config;
    }

    public Sender getSender() {
        return sender;
    }

    public ServiceRegisteActor getServiceRegisteActor() {
        return serviceRegisteActor;
    }

    public ConfigFetchActor getConfFetchActor() {
        return confFetchActor;
    }

    public ClientInstanceInfo getInstanceInfo() {
        return instanceInfo;
    }

    public CacheCompareActor getCacheCompareActor() {
        return cacheCompareActor;
    }
}
