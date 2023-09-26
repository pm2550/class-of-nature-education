package com.program.server.webSocket;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

@Component
public class websocket implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connection established.");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 处理接收到的消息
        System.out.println("Received message: " + message.getPayload());

        // 发送响应消息
        session.sendMessage(new TextMessage("Hello, client!"));
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("WebSocket connection closed.");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("WebSocket transport error.");
    }
}