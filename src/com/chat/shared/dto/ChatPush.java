package com.chat.shared.dto;

import java.io.Serializable;

public class ChatPush implements Serializable {
    private String login;
    private String sessionId;
    private PushType type;
    private String textMessage;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public PushType getType() {
        return type;
    }

    public void setType(PushType type) {
        this.type = type;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
