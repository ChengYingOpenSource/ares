package com.cy.ares.spcp.actor.fetcher;

import com.cy.ares.spcp.protocol.DataItem;

/**
 * 前期有值，后期删除，是否推送事件给客户端 ?? TODO
 * 
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年5月6日 下午2:22:15
 * @version V1.0
 */
public interface Listener {

    public void receive(DataItem item);

}
