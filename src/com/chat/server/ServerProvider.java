package com.chat.server;


import com.chat.server.webSocket.ChatSessionManager;
import com.google.gson.Gson;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Properties;

/**
 * Created by p.stuly on 09.02.2021.
 */
public class ServerProvider {
    public Properties prop = new Properties();
    public String wssAllowOrigin = "*";
    public String httpAllowOrigin = "*";
    public Gson gson = new Gson();
    public HashMap<String, String> usersOnline = new HashMap();
    private static ServerProvider instance;
    public ChatSessionManager chatSessionsManager;
    public JedisPool pool;

    public synchronized static ServerProvider getInstance() {
        if (instance == null) {
            instance = new ServerProvider();
        }
        return instance;
    }

    public ServerProvider() {
        chatSessionsManager = new ChatSessionManager();
        System.out.println("start redis");
        try {
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(20);
            pool = new JedisPool(poolConfig, "localhost");
        } catch (Exception e) {
            System.out.println("err jedis");
        }
        System.out.println("r OK!");

    }

}
