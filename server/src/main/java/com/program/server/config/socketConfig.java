package com.program.server.config;

import com.program.server.handler.ForwardHandler;
import com.program.server.handler.MainHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket
public class socketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MainHandler(), "/hello").setAllowedOrigins("*");
        registry.addHandler(new ForwardHandler(), "/sendReply").setAllowedOrigins("*");
    }


}

