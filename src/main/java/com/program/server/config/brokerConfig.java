//package com.program.server.config;
//
//
//import org.springframework.context.annotation.Configuration;
//        import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//        import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//        import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//        import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class brokerConfig implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic"); // 启用简单的消息代理，可以用于订阅消息
//       config.setApplicationDestinationPrefixes("/app"); // 客户端发送消息的前缀
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/hello");// 注册 WebSocket 端点，客户端通过这个端点连接
//    }
//}
