package com.program.server.handler;

import com.alibaba.fastjson.JSON;
import com.program.server.domain.message;
import com.program.server.service.videoService;
import com.program.server.utils.SessionManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ForwardHandler extends TextWebSocketHandler {
    private static videoService videoService;

    @Autowired
    public void setvideoService(videoService videoService) {
        ForwardHandler.videoService = videoService;
    }
    private static SessionManager sessionManager;

    @Autowired
    public void setSessionManager(SessionManager sessionManager) {
        ForwardHandler.sessionManager = sessionManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String receivedMessage = message.getPayload();
        System.out.println("Received: " + receivedMessage);
        com.program.server.domain.message Msg = JSON.parseObject(receivedMessage, message.class);
//        videoService.setupPipeline();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}