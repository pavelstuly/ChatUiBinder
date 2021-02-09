package com.chat.server.webSocket;


import com.chat.server.ServerProvider;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value = "/socket/{client-id}", configurator = WebsocketConfigurator.class)
public class SocketEndPoint {
    private String id;
    private Session session;

    public SocketEndPoint() {
    }

    public WebSocketManager getManager() {
        return ServerProvider.getInstance().chatSessionsManager;

    }

    @OnOpen
    public void start(Session session, @PathParam("client-id") String id) {
        System.out.println("::OnOpen::" + id);
        this.id = id;
        this.session = session;
        this.session.setMaxIdleTimeout(3600000);
        getManager().addConnection(id, this);
    }

    @OnClose
    public void end(Session session, CloseReason closeReason) {
        System.out.println("::OnClose:: " + id + " reason_code=" + closeReason.getCloseCode() + " reason=" + closeReason.toString());
        if (!closeReason.getCloseCode().equals(CloseReason.CloseCodes.NORMAL_CLOSURE)) {
            getManager().removeConnection(id);
        }
    }

    @OnError
    public void error(Session session, Throwable throwable) {
        System.out.println("::OnError:: " + id + " " + throwable.getMessage());
    }

    @OnMessage
    public void incoming(String message) {
        System.out.println("::OnMessage::" + session.getId() + " " + message);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

}