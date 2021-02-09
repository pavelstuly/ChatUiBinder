package com.chat.server.webSocket;


import com.chat.server.ServerProvider;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.ArrayList;
import java.util.List;


public class WebsocketConfigurator extends ServerEndpointConfig.Configurator {
    static final List<String> METHODS = new ArrayList<String>() {
        {
            add("GET");
        }

        {
            add("PUT");
        }

        {
            add("POST");
        }

        {
            add("DELETE");
        }

        {
            add("OPTIONS");
        }
    };

    static final List<String> HEADERS = new ArrayList<String>() {
        {
            add("Content-ParamType");
        }

        {
            add("Content-Range");
        }

        {
            add("Content-Disposition");
        }

        {
            add("Content-Description");
        }
    };

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        response.getHeaders().put("Access-Control-Allow-Origin", new ArrayList<String>() {
            {
                add(ServerProvider.getInstance().wssAllowOrigin);
            }
        });
        response.getHeaders().put("Access-Control-Allow-Methods", METHODS);
        response.getHeaders().put("Access-Control-Allow-Headers", HEADERS);
    }
}