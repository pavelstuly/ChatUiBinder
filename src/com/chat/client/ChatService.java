package com.chat.client;

import com.chat.shared.dto.Message;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("ChatService")
public interface ChatService extends RemoteService {
    void init();
    int checkLogin(String login);
   List<Message> getMessages();
    void setUser(String sessionId,String login);
    void sendMessage(String login,String sessionId,String text);
     List<String> getUsers();
    public static class App {
        private static ChatServiceAsync ourInstance = GWT.create(ChatService.class);

        public static synchronized ChatServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
