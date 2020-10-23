package com.cy.ares.spcp.client;

import com.cy.ares.spcp.context.RefreshContext;
import com.cy.ares.spcp.message.EventMsg.Event;
import com.cy.ares.spcp.net.Callback;

/**
 * 回调操作，纯粹打印日志
 * 
 * @author maoxq
 *
 * @Description
 *
 * @date 2019年5月5日 上午9:36:41
 * @version V1.0
 */
public class LogCallback implements Callback<Event> {

    @Override
    public void success(Event obj, RefreshContext contxt) {

    }

    @Override
    public void error(RefreshContext contxt, Exception e) {
        // TODO Auto-generated method stub

    }

}
