package com.chat.client;

import com.chat.shared.dto.Message;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;
import java.util.Set;

public interface ChatServiceAsync {

    void init(AsyncCallback<Void> asyncCallback);

    void checkLogin(String login, AsyncCallback<Integer> async);

    void setUser(String sessionId, String login, AsyncCallback<Void> async);

    void getUsers(AsyncCallback<List<String>> async);

    void sendMessage(String login,String sessionId,String text,AsyncCallback<Void> async);

    void getMessages(AsyncCallback<List<Message>> async);
}
