package com.chat.shared.dto;

import java.io.Serializable;


public class PushMessage implements Serializable {
    private PushType type;

    public PushMessage() {
    }

    public PushMessage(PushType type) {
        this.type = type;
    }

    public PushType getType() {
        return type;
    }

    public void setType(PushType type) {
        this.type = type;
    }
}
