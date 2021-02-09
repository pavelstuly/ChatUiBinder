package com.chat.client.widgets;

import com.google.gwt.user.client.ui.*;

public class UserWidget extends HorizontalPanel
{
    public UserWidget(String login, String icon,int width) {
        Image image=new Image(""+icon);
this.setStyleName("user_widget_style");
        Label label=new Label(login);
        this.add(image);
        this.add(label);
        this.setWidth(""+width);

    }

}
