package com.chat.client.widgets;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class MessagesWidget extends ScrollPanel {
    VerticalPanel panel = new VerticalPanel();

    public MessagesWidget(String name, String icon, String width) {
        Image image = new Image("" + icon);
        Label label = new Label(name);
        HorizontalPanel HeaderPanel = new HorizontalPanel();
        HeaderPanel.add(image);
        HeaderPanel.add(label);
        this.setWidth(width);
        this.add(panel);
        this.setHeight("" + (Window.getClientHeight() - 400));
        panel.setStyleName("elements_diff_style");
        this.setStyleName("users_widget_style");

    }

    public void addMessage(Widget message) {
        panel.add(message);
    }

    public void clearMsgs() {
        panel.clear();
    }
}
