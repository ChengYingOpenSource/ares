package com.cy.ares.cluster.registe;

import com.alibaba.fastjson.JSON;
import com.cy.ares.cluster.error.ServerBootstarpException;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Mxq
 * @Date 2019/11/11 13:53
 */
public class ServicePersistence {

    private String redisLink;
    private RedissonClient redisson;

    public ServicePersistence(String redisLink) {
        if (StringUtils.isBlank(redisLink)) {
            return;
        }
        this.redisLink = redisLink;
    }

    public void init() {
        String[] configs = redisLink.split("&");
        Map<String, String> m = new HashMap<>();
        for (String e : configs) {
            String[] a = e.split("=");
            m.put(a[0], a[1]);
        }
        String mode = m.get("mode");
        String nodes = m.get("nodes");
        List<String> ls = JSON.parseArray(nodes, String.class);
        if (ls == null || ls.isEmpty()) {
            throw new ServerBootstarpException("service persistence redisLink ndoes error!");
        }
        // password
        mode = StringUtil.isBlank(mode) ? "single" : mode;
        if (mode.equals("single")) {
            Config config = new Config();
            config.useSingleServer().setAddress(ls.get(0));
            redisson = Redisson.create(config);
        } else if (mode.equals("cluster")) {
            Config config = new Config();
            config.useClusterServers()
                .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress(ls.toArray(new String[ls.size()]));
            redisson = Redisson.create(config);
        } else if (mode.equals("replicated")) {
            Config config = new Config();
            config.useReplicatedServers()
                .setScanInterval(2000) // 主节点变化扫描间隔时间
                .addNodeAddress(ls.toArray(new String[ls.size()]));
            redisson = Redisson.create(config);
        } else {
            throw new ServerBootstarpException("service persistence redisLink mode error!");
        }
    }

    public void registe() {

    }

}
