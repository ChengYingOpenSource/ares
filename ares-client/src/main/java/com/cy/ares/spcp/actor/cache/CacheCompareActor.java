package com.cy.ares.spcp.actor.cache;

import com.cy.ares.spcp.context.SpcpContext;
import org.apache.commons.codec.digest.Md5Crypt;

/**
 * 定时和server端的key进行比较;
 * 也有可能 比较的时候正好进行着推送; TODO 会不会扰乱推送的key
 *
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年4月29日 下午3:51:12
 * @version V1.0
 */
@Deprecated
public class CacheCompareActor {

    private SpcpContext ctx;
    private DataConfCache cache;

    public static final String semicolon = ";";

    public CacheCompareActor(SpcpContext ctx,DataConfCache confCache) {
        this.ctx = ctx;
        this.cache = confCache;
    }

    //
    public void compare(){

        // for all group

        String digest = Md5Crypt.md5Crypt("xxxx".getBytes());


    }






}
