package com.program.server.utils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class sessionManager {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addSession(String username, WebSocketSession session) {
        sessions.put(username, session);
    }

    public void removeSession(String username) {
        sessions.remove(username);
    }

    public WebSocketSession getSession(String username) {
        return sessions.get(username);
    }
}
