package com.chat.client.widgets;

import com.google.gwt.user.client.ui.*;

public class MessageWidget extends HorizontalPanel {

    private TextBox textBox = new TextBox();
    private CheckBox checkBox = new CheckBox();
    private String login;
    private Long timestamp;
    private String icon;
    private String text;
    private String width;

    public MessageWidget(String login, String icon, String text, long timestamp, String width) {
        Image image = new Image("" + icon);
        this.setStyleName("message_widget_style");
        TextArea msg = new TextArea();
        msg.setStyleName("input_style");
        msg.setAlignment(ValueBoxBase.TextAlignment.LEFT);
        msg.setWidth("" + (Integer.parseInt(width) - 86));
        msg.setText(text);
        msg.setReadOnly(true);
        Label label = new Label(login);
        VerticalPanel panel = new VerticalPanel();
        panel.add(label);
        panel.add(msg);
        this.add(image);
        this.add(panel);
        this.width = width;


    }

    public String getWidth() {
        return width;
    }

    @Override
    public void setWidth(String width) {
        this.width = width;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
