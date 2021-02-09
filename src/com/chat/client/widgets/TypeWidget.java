package com.chat.client.widgets;

import com.chat.client.ChatService;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.ValueBoxBase;

public class TypeWidget extends HorizontalPanel {
    public TypeWidget(String length, String login, String sess) {
        this.setWidth(length);
        TextArea input = new TextArea();
        input.setWidth("" + (Integer.parseInt(length) - 64));
        input.setHeight("200");
        input.setAlignment(ValueBoxBase.TextAlignment.LEFT);
        input.setStyleName("input_style");
        this.add(input);
        input.setFocus(true);
        input.getElement().setPropertyString("placeholder", "Вводите сообщение сюда...");
        Image submit = new Image("images/send.png");
        this.add(submit);
        submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (input.getText().length() > 0) {
                    doCallback(login, sess, input.getText());
                    input.setText("");
                    input.setCursorPos(0);
                }
            }
        });


    }

    private void doCallback(String login, String sess, String text) {
        ChatService.App.getInstance().sendMessage(login, sess, text, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(Void result) {

            }
        });
    }

}
