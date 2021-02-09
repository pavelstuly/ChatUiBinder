package com.chat.server;

import com.chat.client.ChatService;
import com.chat.shared.dto.ChatPush;
import com.chat.shared.dto.Message;
import com.chat.shared.dto.PushType;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.FileInputStream;
import java.util.*;

public class ChatServiceImpl extends RemoteServiceServlet implements ChatService {

    public int checkLogin(String login) {

        if (ServerProvider.getInstance().usersOnline.containsKey(login)) return 2;
        else if (login.length() >= 8 & login.length() < 30) return 1;
        else return 3;
    }

    public List<String> getUsers() {
        try {
            List<String> out = new ArrayList<>();
            for (String a : ServerProvider.getInstance().usersOnline.keySet()) {
                out.add(a);
            }
            return out;
        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
            return null;
        }
    }

    public List<Message> getMessages() {
        try {
            List<Message> out = new ArrayList<>();
            JedisPool pool = ServerProvider.getInstance().pool;
            Jedis j = pool.getResource();
            Set<String> keys = j.keys("test:message:" + "*");
            String[] wordsArray = keys.toArray(new String[keys.size()]);
            Arrays.sort(wordsArray);
            List<String> values = j.mget(wordsArray);
            for (String json : values) {
                out.add(ServerProvider.getInstance().gson.fromJson(json, Message.class));
            }
            j.close();
            return out;
        } catch (Exception e) {
            e.getMessage();
            System.out.println("error" + e.getMessage());
            return null;
        }
    }

    public void setUser(String sessionId, String login) {
        ServerProvider.getInstance().chatSessionsManager.setUser(sessionId, login);
        ServerProvider.getInstance().usersOnline.put(login, sessionId);

    }

    public void sendMessage(String login, String sessionId, String text) {
        ChatPush push = new ChatPush();
        push.setType(PushType.MESSAGE);
        push.setTextMessage(text);
        push.setLogin(login);
        push.setSessionId(sessionId);


        ServerProvider.getInstance().writeRedisEvent(login, sessionId, text);

        ServerProvider.getInstance().chatSessionsManager.pushAll(push);


    }

    public void init() {


    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String path = config.getServletContext().getRealPath("/") + "WEB-INF\\";

        path = path.replace("\\", "//");
        System.out.println();

        System.out.println("path=" + path);

        try {
            FileInputStream fis = new FileInputStream(path + "config.properties");
            Properties properties = new Properties();
            properties.load(fis);
            ServerProvider.getInstance().initRedis(properties);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        System.out.println("INIT OK");


    }
}