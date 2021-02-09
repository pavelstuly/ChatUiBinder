package com.chat.client;

import com.chat.shared.dto.ChatPush;
import com.chat.shared.dto.Message;
import com.chat.shared.dto.PushType;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p.stuly on 09.02.2021.
 */
public class JsonParser {
    public static ChatPush parsePush(JSONValue value) {
        ChatPush chatPush = new ChatPush();
        JSONObject jsonObject;
        JSONValue jsonValue = value;
        if ((jsonObject = jsonValue.isObject()) != null) {
            jsonValue = jsonObject.get("login");
            if (jsonValue != null) {
                String tt = jsonValue.isString().stringValue();
                chatPush.setLogin(tt);
            }

            jsonValue = jsonObject.get("sessionId");
            if (jsonValue != null) {
                String tt = jsonValue.isString().stringValue();
                chatPush.setSessionId(tt);
            }

            jsonValue = jsonObject.get("textMessage");
            if (jsonValue != null) {
                String tt = jsonValue.isString().stringValue();
                chatPush.setTextMessage(tt);
            }

            jsonValue = jsonObject.get("type");
            if (jsonValue != null) {
                PushType st = PushType.valueOf(jsonValue.isString().stringValue());
                chatPush.setType(st);
            }


        }
        return chatPush;
    }

    public static Message parseMessage(JSONValue value) {
        Message message=new Message();
        JSONObject jsonObject;
        JSONValue jsonValue = value;
        if ((jsonObject = jsonValue.isObject()) != null) {
            jsonValue = jsonObject.get("login");
            if (jsonValue != null) {
                String tt = jsonValue.isString().stringValue();
                message.setLogin(tt);
            }

            jsonValue = jsonObject.get("text");
            if (jsonValue != null) {
                String tt = jsonValue.isString().stringValue();
                message.setText(tt);
            }
/*
            jsonValue = jsonObject.get("timestamp");
            if (jsonValue != null) {
                long st = (long)jsonValue.isNumber().doubleValue();
                message.setTimestamp(st);
            }*/


        }
        return message;
    }
}