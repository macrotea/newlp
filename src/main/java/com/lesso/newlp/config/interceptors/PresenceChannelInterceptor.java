package com.lesso.newlp.config.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sean on 4/18/2014.
 */
public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {

    Logger logger = LoggerFactory.getLogger(PresenceChannelInterceptor.class);

    public Set<String> connectedUsers = new HashSet<String>();

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);

        // ignore non-STOMP messages like heartbeat messages
        if(sha.getCommand() == null) {
            return;
        }

        String sessionId = sha.getSessionId();

        switch(sha.getCommand()) {
            case CONNECT:
                connectedUsers.add(sha.getUser().getName());
                logger.debug("STOMP Connect [sessionId: " + sessionId + "]");
                break;
            case CONNECTED:
                logger.debug("STOMP Connected [sessionId: " + sessionId + "]");
                break;
            case DISCONNECT:
                if(null != sha.getUser()){
                    connectedUsers.remove(sha.getUser().getName());
                }
                logger.debug("STOMP Disconnect [sessionId: " + sessionId + "]");
                break;
            default:
                break;

        }
    }
}