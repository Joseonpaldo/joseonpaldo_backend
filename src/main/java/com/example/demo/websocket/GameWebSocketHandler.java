package com.example.demo.websocket;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Controller
@Component
public class GameWebSocketHandler extends TextWebSocketHandler {
    private static final Map<String, Set<WebSocketSession>> gameRoom = new ConcurrentHashMap<>();

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String room = getRoom(session);
        gameRoom.computeIfAbsent(room, k -> new CopyOnWriteArraySet<>()).add(session);
    }

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String room = getRoom(session);

        for (WebSocketSession webSocketSession : gameRoom.get(room)) {
            if (webSocketSession.isOpen()) {
                String mo = message.getPayload();
                webSocketSession.sendMessage(new TextMessage(mo));
            }
        }
    }

    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status)
            throws Exception {
        String room = getRoom(session);
        gameRoom.getOrDefault(room, new CopyOnWriteArraySet<>()).remove(session);
    }

    public String getRoom(WebSocketSession session) {
        return session.getUri().getPath().split("/")[2];
    }
}
