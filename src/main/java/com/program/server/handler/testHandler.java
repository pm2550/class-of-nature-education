package com.program.server.handler;

import com.program.server.domain.message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.program.server.utils.sessionManager;
import com.alibaba.fastjson.JSON;



@Component
public class testHandler extends TextWebSocketHandler {

    @Autowired
    private sessionManager sessionManager;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String receivedMessage = message.getPayload();
        System.out.println("Received: " + receivedMessage);
        // 将JSON字符串转换为Message对象
        message Msg = JSON.parseObject(receivedMessage, message.class);

        // 访问Message对象的属性
        session.getAttributes().put("username", Msg.getName());
        sessionManager.addSession(Msg.getName(),session);
        session.sendMessage(new TextMessage("Hello"+Msg.getName()+", I have received: " + Msg.getMsg()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get("username");
        if (username != null) {
            sessionManager.removeSession(username);
        }
    }
}
