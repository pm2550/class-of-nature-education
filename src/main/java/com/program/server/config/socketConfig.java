//package com.program.server.config;
//
//import com.program.server.controller.videoController;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//@Configuration
//@EnableWebSocket
//public class socketConfig implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler((WebSocketHandler) new videoController(), "/hello").setAllowedOrigins("*");
//    }
//
//
//}
//
