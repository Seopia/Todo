package com.yunha.backend.config;

import com.yunha.backend.security.jwt.WebSocketFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final WebSocketFilter webSocketFilter;

    public WebSocketConfig(WebSocketFilter webSocketFilter) {
        this.webSocketFilter = webSocketFilter;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");    //구독
        config.setApplicationDestinationPrefixes("/app");       //send
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/chat")
            .addInterceptors(webSocketFilter)
            .setAllowedOrigins("http://localhost:3000")
            .withSockJS();
    }
}
