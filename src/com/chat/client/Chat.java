package com.chat.client;

import com.chat.client.widgets.LoginWidget;
import com.chat.client.widgets.MessageWidget;
import com.chat.client.widgets.UserWidget;
import com.chat.client.widgets.UsersWidget;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Chat implements EntryPoint {


    public void onModuleLoad() {


        LoginWidget lw=new LoginWidget();
        RootPanel.get("slot2").add(lw);



    }



}
