package com.chat.server.webSocket;


import com.chat.shared.dto.PushMessage;

public interface WebSocketManager<T extends PushMessage> {
    void addConnection(String sessionId, SocketEndPoint socket);

    void removeConnection(String sessionId);

    void pushTestMessage(String sessionId, String textMessage);

    void push(String sessionId, T pushMessage);
}