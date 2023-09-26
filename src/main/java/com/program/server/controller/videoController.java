package com.program.server.controller;
import com.program.server.service.videoService;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import com.program.server.utils.sessionManager;
import org.springframework.web.socket.WebSocketSession;

import java.security.Principal;

@Controller
public class videoController {

    @Autowired
    private videoService videoservice;

    private SimpMessagingTemplate messagingTemplate;
    private sessionManager sessionManager;

    @Autowired
    public void MyWebSocketController(SimpMessagingTemplate messagingTemplate, sessionManager sessionManager) {
        this.messagingTemplate = messagingTemplate;
        this.sessionManager = sessionManager;
    }
    @MessageMapping("/hello")
    public void reply(String message)
    {
        String response="hello, l have received:"+message;
        System.out.println(response);
        messagingTemplate.convertAndSend("/hello", response);
    }

    @MessageMapping("/myWebSocket")
    public void handleWebSocketMessage(String message, Principal principal) {
        String username = principal.getName();
        WebSocketSession session = sessionManager.getSession(username);

        if (session != null) {
            messagingTemplate.convertAndSendToUser(username, "/topic/myWebSocket", "You said: " + message);
        }
    }
}
