package com.chat.client;

import com.chat.client.widgets.LoginWidget;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Chat implements EntryPoint {


    public void onModuleLoad() {


        LoginWidget lw = new LoginWidget();
        RootPanel.get("slot2").add(lw);


    }


}
