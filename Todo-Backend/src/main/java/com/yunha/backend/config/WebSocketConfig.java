package com.yunha.backend.config;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");    //구독
        config.setApplicationDestinationPrefixes("/app");       //send
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/chat").withSockJS();
    }
}
