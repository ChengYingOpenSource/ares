package com.cy.ares.cluster.error;
import io.netty.channel.Channel;



public class NetException extends RuntimeException {
    
    private Channel channel;
    
    public NetException() {
        super();
    }

    public NetException(String message) {
        super(message);
    }

    public NetException(String message, Throwable cause) {
        super(message, cause);
    }

    public NetException(Throwable cause) {
        super(cause);
    }
    
    public NetException(Channel channel) {
        super();
        this.channel = channel;
    }

    public NetException(String message,Channel channel) {
        super(message);
        this.channel = channel;
    }

    public NetException(String message, Throwable cause,Channel channel) {
        super(message, cause);
        this.channel = channel;
    }

    public NetException(Throwable cause,Channel channel) {
        super(cause);
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }
    
    
}
