package com.program.server.handler;

import com.program.server.domain.message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.program.server.utils.SessionManager;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.ServerSocket;

@Component
public class MainHandler extends TextWebSocketHandler {

    private static SessionManager sessionManager;

    @Autowired
    public void setSessionManager(SessionManager sessionManager) {
      MainHandler.sessionManager = sessionManager;
    }



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }
    private int selectAvailablePort() {
        for (int port = 10000; port < 20000; port++) {
            try (ServerSocket socket = new ServerSocket(port)) {
                return port;  // 如果成功，则这个端口是可用的
            } catch (IOException e) {
                // 如果捕获到异常，则此端口不可用，尝试下一个端口
            }
        }
        throw new RuntimeException("No available port found");
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String receivedMessage = message.getPayload();
        System.out.println("Received: " + receivedMessage);
        // 将JSON字符串转换为Message对象
        message Msg = JSON.parseObject(receivedMessage, message.class);
        if(Msg.getType().equals("hello"))
        {
            session.getAttributes().put("user", Msg.getUser());
            session.getAttributes().put("username", Msg.getName());
            sessionManager.addSession(Msg.getName(),session);
            System.out.println(session.getId()+"///"+Msg.getName()+"进入");
            System.out.println(Msg.getType());
            session.sendMessage(new TextMessage("Hello "+Msg.getName()+", I have received: " + Msg.getMsg()));
        }
        // 访问Message对象的属性

        else if(Msg.getType().equals("order")) {
            session.sendMessage(new TextMessage("received order " + Msg.getMsg() +
                    " from " + session.getAttributes().get("username")));
            if(Msg.getMsg().equals("open")) {
                int port = selectAvailablePort();
                Msg.setMsg("video send to:"+port);
                String sendMessage=JSON.toJSONString(Msg);
                WebSocketSession sendSession= sessionManager.getSession(Msg.getTarget());
                if(sendSession==null)
                    session.sendMessage(new TextMessage("对象错误"));
                else {
                    sendSession.sendMessage(new TextMessage(sendMessage));
                    System.out.println("push" + sendMessage + "to " + Msg.getTarget());
                }
            }
        else if(Msg.getMsg().equals("close")){
                Msg.setMsg("stop video");
                String sendMessage=JSON.toJSONString(Msg);
                WebSocketSession sendSession= sessionManager.getSession(Msg.getTarget());
                if(sendSession==null)
                    session.sendMessage(new TextMessage("对象错误"));
                    else {
                    sendSession.sendMessage(new TextMessage(sendMessage));
                    System.out.println("push" + sendMessage + "to " + Msg.getTarget());
                }
            }

        }
        else if(Msg.getType().equals("reply")){

        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get("username");
        if (username != null) {
            System.out.println(session.getId()+"///"+username+"离开");
            sessionManager.removeSession(username);
        }
    }
}
