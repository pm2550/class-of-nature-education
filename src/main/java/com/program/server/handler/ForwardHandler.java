package com.program.server.handler;

import com.alibaba.fastjson.JSON;
import com.program.server.domain.message;
import com.program.server.service.RtspService;
import com.program.server.service.videoService;
import com.program.server.service.webService;
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
import com.program.server.handler.MainHandler;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ForwardHandler extends TextWebSocketHandler {

    private static RtspService rtspService;
    @Autowired
    public void setvideoService(RtspService rtspService) {
        ForwardHandler.rtspService = rtspService;
    }
    private static SessionManager sessionManager;

    @Autowired
    public void setWebService(SessionManager sessionManager) {
        ForwardHandler.sessionManager = sessionManager;
    }
    private static com.program.server.service.webService webService;

    @Autowired
    public void setSessionManager(webService webService) {
        ForwardHandler.webService = webService;
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String receivedMessage = message.getPayload();
        System.out.println("receiveReply: " + receivedMessage);
        if(!receivedMessage.equals("hello")) {
            com.program.server.domain.message Msg = JSON.parseObject(receivedMessage, message.class);
            if(Msg.getType().equals("reply"))
            {
                int port = webService.selectAvailablePort();
                System.out.println("输入数据为"+Msg.getPort()+String.valueOf(port));
                ExecutorService executor = Executors.newSingleThreadExecutor();
                    WebSocketSession sendSession= sessionManager.getSession(Msg.getTarget());
                executor.submit(() -> {
                    int a=rtspService.startServer(Msg.getPort(), String.valueOf(port), "/default");
                    System.out.println(a);
                });
                System.out.println("开始rtsp");
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}