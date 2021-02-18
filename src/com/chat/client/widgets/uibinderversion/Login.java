package com.chat.client.widgets.uibinderversion;

import com.chat.client.ChatService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class Login extends Composite {

    private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

    @UiTemplate("uixml/Login.ui.xml")
    interface LoginUiBinder extends UiBinder<Widget, Login> {
    }


    @UiField(provided = true)
    final CssRes res;

    public Login() {
        this.res = GWT.create(CssRes.class);
        res.style().ensureInjected();
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    TextBox loginBox;

    @UiHandler("buttonSubmit")
    void doClickSubmit(ClickEvent event) {


        ChatService.App.getInstance().checkLogin(loginBox.getText(), new AsyncCallback<Integer>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("err" + caught.getMessage());
            }

            @Override
            public void onSuccess(Integer result) {
                switch (result) {
                    case 1:
                        RootPanel.get().clear();
                        RootPanel.get().add(new MainChatPanel(loginBox.getText()));
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


}




