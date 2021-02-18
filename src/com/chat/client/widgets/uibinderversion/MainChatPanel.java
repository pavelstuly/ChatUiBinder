package com.chat.client.widgets.uibinderversion;

import com.chat.client.ChatService;
import com.chat.client.JsonParser;
import com.chat.client.widgets.MessageWidget;
import com.chat.client.widgets.UserWidget;
import com.chat.shared.dto.ChatPush;
import com.chat.shared.dto.Message;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.List;

public class MainChatPanel extends Composite {
    private static String sessionId;
    public static String lg;
    private static ChatUibinder uiBinder = GWT.create(ChatUibinder.class);
    @UiField
    static VerticalPanel userList;
    @UiField
    static VerticalPanel messageList;
    @UiField
    static ScrollPanel msgCont;
    @UiField
    static ScrollPanel userCont;
    @UiField
    static TextArea input;
    @UiField
    static Image submit;

    @UiTemplate("uixml/MainChatPanel.ui.xml")
    interface ChatUibinder extends UiBinder<Widget, MainChatPanel> {
    }


    public MainChatPanel(String login) {

        initWidget(uiBinder.createAndBindUi(this));
        lg = login;

        this.sessionId = generateSessionId();
        expose();
        connectWS(sessionId);

        userList.add(new UserWidget("yyyy", "images/chat.png", 200));
        msgCont.setHeight("" + (Window.getClientHeight() - 500));
        userCont.setHeight("" + (Window.getClientHeight() - 200));
        input.setWidth("535");
        input.setHeight("300");
        submit.setUrl("images/send.png");

    }


    @UiHandler("submit")
    void doClickSubmit(ClickEvent event) {

        ChatService.App.getInstance().sendMessage(lg, sessionId, input.getText(), new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
            }

            @Override
            public void onSuccess(Void result) {
                input.setText("");
            }
        });


    }

    public static void connect() {

        ChatService.App.getInstance().setUser(sessionId, lg, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("error" + caught.getMessage());
            }

            @Override
            public void onSuccess(Void result) {
                ChatService.App.getInstance().getMessages(new AsyncCallback<List<Message>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("error" + caught.getMessage());
                    }

                    @Override
                    public void onSuccess(List<Message> result) {
                        for (Message m : result) {
                            String imageName = "";
                            if (m.getLogin().equals(lg)) imageName = "images/me_pic.png";
                            else imageName = "images/user_male.png";
                            if (m.getLogin().equals("Чат")) imageName = "images/chat.png";

                            messageList.add(new MessageWidget(m.getLogin(), imageName, m.getText(), 1242353, "600"));

                        }
                        msgCont.scrollToBottom();
                    }
                });
            }
        });
    }

    public static void OnPushReceived(String pushMsg) {
        ChatPush push = (new JsonParser().parsePush(JSONParser.parseStrict(pushMsg)));
        switch (push.getType()) {
            case INFO: {
                ChatService.App.getInstance().getUsers(new AsyncCallback<List<String>>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert(caught.getMessage());
                    }

                    @Override
                    public void onSuccess(List<String> result) {
                        userList.clear();
                        for (String s : result) {

                            UserWidget userWidget = new UserWidget(s, (lg.equals(s)) ? "images/me_pic.png" : "images/user_male.png", 200);
                            userList.add(userWidget);


                        }
                        messageList.add(new MessageWidget("Чат", "images/chat.png", push.getTextMessage(), 43534, "600"));
                        msgCont.scrollToBottom();
                    }
                });
            }
            break;
            case REG: {
                connect();
            }
            break;
            case MESSAGE: {
                messageList.add(new MessageWidget(push.getLogin(), (lg.equals(push.getLogin())) ? "images/me_pic.png" : "images/user_male.png", push.getTextMessage(), 43534, "600"));
                msgCont.scrollToBottom();
            }
            break;
            default:
                Window.alert("Неизвестный пуш");
                break;
        }


    }

    public String getSessionId() {
        return sessionId;
    }

    public native String generateSessionId()/*-{
        return "user-" + $wnd.uuidv4();
    }-*/;

    public native void expose()/*-{
        $wnd.exposedMethod = function (param) {
            @com.chat.client.widgets.uibinderversion.MainChatPanel::OnPushReceived(Ljava/lang/String;)(param);
        }
    }-*/;

    public native void connectWS(String sessionId)/*-{
        $wnd.connect(sessionId);
    }-*/;

}
