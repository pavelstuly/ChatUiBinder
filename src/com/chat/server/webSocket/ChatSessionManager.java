package com.chat.server.webSocket;

import com.chat.server.ServerProvider;
import com.chat.shared.dto.ChatPush;
import com.chat.shared.dto.PushMessage;
import com.chat.shared.dto.PushType;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ChatSessionManager implements WebSocketManager {

    public static ConcurrentHashMap<String, ChatSession> sessions = new ConcurrentHashMap<String, ChatSession>();

    public int getCount() {
        return sessions.size();
    }

    public void destroy() {

    }

    @Override
    public void addConnection(String sessionId, SocketEndPoint socket) {
        ChatSession ss = new ChatSession(sessionId, socket);
        System.out.println("add new " + sessionId);
        ss.setSessionId(sessionId);
        ss.setSocket(socket);
        sessions.put(sessionId, ss);
        ChatPush regPush = new ChatPush();
        regPush.setType(PushType.REG);
        pushTestMessage(sessionId, ServerProvider.getInstance().gson.toJson(regPush));

    }

    public void setUser(String sessionId, String login) {
        ChatSession session = sessions.get(sessionId);
        System.out.println(" setting user for " + sessionId + " lg=" + login);
        session.setLogin(login);
        sessions.replace(sessionId, session);
        System.out.println("OK " + sessions.get(sessionId).getLogin());
        ChatPush push = new ChatPush();
        push.setLogin(login);
        push.setSessionId(sessionId);
        push.setTextMessage("Пользователь " + login + " подключается в чат");
        ServerProvider.getInstance().writeRedisEvent("Чат", sessionId, push.getTextMessage());
        push.setType(PushType.INFO);
        pushAll(push);
    }

    @Override
    public void removeConnection(String sessionId) {
        ChatSession session = sessions.get(sessionId);
        System.out.println("stop session " + sessionId + " !");
        if (session != null) {
            SocketEndPoint socket = session.getSocket();
            if (socket != null && socket.getSession().isOpen()) {
                try {

                    socket.getSession().close();
                } catch (Exception e) {
                }
            }
            sessions.remove(sessionId);
            ChatPush push = new ChatPush();
            push.setLogin(session.getLogin());
            push.setSessionId(sessionId);
            push.setTextMessage("Пользователь " + session.getLogin() + " выходит из чата");
            push.setType(PushType.INFO);
            ServerProvider.getInstance().writeRedisEvent("Чат", sessionId, push.getTextMessage());
            ServerProvider.getInstance().usersOnline.remove(session.getLogin());
            pushAll(push);

        }
    }

    @Override
    public void pushTestMessage(String sessionId, String textMessage) {
        String key = sessions.keySet().stream().filter(s -> s.equals(sessionId)).findFirst().orElse(null);
        if (key != null) {
            ChatSession cSession = sessions.get(sessionId);
            if (cSession != null) {
                SocketEndPoint socket = cSession.getSocket();
                if (socket.getSession().isOpen()) {
                    try {
                        socket.getSession().getBasicRemote().sendText(textMessage);
                    } catch (Exception e) {

                    }
                }
            }
        }

    }


    public synchronized void pushAll(ChatPush push) {

        for (String sessionId : sessions.keySet()) {
            ChatSession cSession = sessions.get(sessionId);
            if (cSession != null) {
                SocketEndPoint socket = cSession.getSocket();
                Session session = socket.getSession();
                if (session != null && session.isOpen()) {
                    String text = ServerProvider.getInstance().gson.toJson(push);
                    try {
                        System.out.println("push to dc.. ok");
                        session.getBasicRemote().sendText(text);
                    } catch (IOException e1) {
                        System.out.println(e1.getStackTrace());

                    }
                }
            }

        }
    }


    @Override
    public synchronized void push(String sessionId, PushMessage pushMessage) {
        ChatSession cSession = sessions.get(sessionId);
        if (cSession != null) {
            System.out.println("Send 2");
            SocketEndPoint socket = cSession.getSocket();
            Session session = socket.getSession();
            if (session != null && session.isOpen()) {
                System.out.println("Send 3");
                String text = ServerProvider.getInstance().gson.toJson(pushMessage);
                try {
                    session.getBasicRemote().sendText(text);
                } catch (IOException e1) {

                }
            }
        }
    }

}