package com.chat.server.webSocket;


public class ChatSession {
    private String sessionId;
    private String login;
    private SocketEndPoint socket;


    public ChatSession(String sessionId, SocketEndPoint socket) {
        this.sessionId = sessionId;
        this.socket = socket;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public SocketEndPoint getSocket() {
        return socket;
    }

    public void setSocket(SocketEndPoint socket) {
        this.socket = socket;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
