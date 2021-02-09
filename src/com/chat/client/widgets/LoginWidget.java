package com.chat.client.widgets;

import com.chat.client.ChatService;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class LoginWidget extends VerticalPanel {
    TextBox login = new TextBox();
    Button submit = new Button("вход");
    Image loginImg = new Image("images/login_img.png");

    public LoginWidget() {

        login.setMaxLength(30);
        //login.setWidth("30");
        // submit.setWidth("10");
        //  submit.setHeight("5");
        this.add(loginImg);
        this.add(login);
        this.add(submit);
        this.setStyleName("login_style");
        login.setFocus(true);
        login.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == 13) doCallback();
            }
        });
        submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                doCallback();
            }
        });

    }

    private void doCallback() {
        ChatService.App.getInstance().checkLogin(login.getText(), new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Ошибка:" + caught.getMessage());
            }

            @Override
            public void onSuccess(Integer result) {
                switch (result) {
                    case 1:
                        attemptLogin(login.getText());
                        break;
                    case 2:
                        Window.alert("Логин уже занят, используйте другой");
                        break;
                    case 3:
                        Window.alert("Длина логина не менее 8 и не более 30 символов");
                        break;
                    default:
                        Window.alert("Неизвестный код ошибки");
                        break;
                }
            }
        });
    }

    private void attemptLogin(String text) {
        RootPanel.get("slot2").clear();
        RootPanel.get("slot2").add(new MainWidget(text));
    }
}
