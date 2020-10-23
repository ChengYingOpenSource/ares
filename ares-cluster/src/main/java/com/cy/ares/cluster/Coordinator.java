package com.cy.ares.cluster;

/**
 * 
 * client -> msg -> Event -> 
 * 
 * 
 * @author maoxq
 *
 * @Description 
 *
 * @date 2019年5月8日 下午9:34:44
 * @version V1.0
 */
public interface Coordinator {
    
    public static final String CHANNEL_EXCEPTION = "CHANNEL_EXCEPTION";
    public static final String CHANNEL_IDLE = "CHANNEL_IDLE";
    public static final String CHANNEL_ACTIVE = "CHANNEL_ACTIVE";
    public static final String CHANNEL_INACTIVE = "CHANNEL_INACTIVE";
    
    public static final String HEARTBEAT_DOWN = "HEARTBEAT_DOWN";
    
//    public void messageIn(EventMsg.Event eventIn) ;
//    
//    public void messageOut(EventMsg.Event eventOut) ;
//    
//    public void exceptionHandle(NetException exception);
    
}
