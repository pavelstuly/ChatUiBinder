package com.chat.client.widgets;

import com.chat.client.ChatService;
import com.chat.client.JsonParser;
import com.chat.shared.dto.ChatPush;
import com.chat.shared.dto.Message;
import com.chat.shared.dto.PushType;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.ArrayList;
import java.util.List;

public class MainWidget extends HorizontalPanel {
    private String sessionId;
    public static String lg;
    public static List<String> userList = new ArrayList();
    public static MessagesWidget mv = null;
    public static UsersWidget uv = null;

    public MainWidget(String login) {
        uv = new UsersWidget("пользователи онлайн", "images/ppanel_icon.png", "200");
        mv = new MessagesWidget("сообщения", "images/ppanel_icon.png", "" + (Window.getClientWidth() - 240));
        this.add(uv);
        VerticalPanel pl = new VerticalPanel();
        pl.add(mv);
        pl.add(new TypeWidget("" + (Window.getClientWidth() - 240), login, getSessionId()));
        this.add(pl);

        lg = login;

        this.sessionId = generateSessionId();
        expose();
        connectWS(sessionId);
        Timer timer = new Timer() {
            @Override
            public void run() {


                ChatService.App.getInstance().setUser(sessionId, login, new AsyncCallback<Void>() {
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
                                    mv.addMessage(new MessageWidget(m.getLogin(), lg.equals(m.getLogin()) ? "images/me_pic.png" : "images/user_male.png", m.getText(), 1242353, "" + (Window.getClientWidth() - 310)));

                                }
                                mv.scrollToBottom();
                            }
                        });
                    }
                });
            }
        };
        //time to initialize...
        timer.schedule(1000);


    }

    public static void OnPushReceived(String pushMsg) {
        ChatPush push = (new JsonParser().parsePush(JSONParser.parseStrict(pushMsg)));
        if (push.getType() == PushType.INFO) {
            ChatService.App.getInstance().getUsers(new AsyncCallback<List<String>>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert(caught.getMessage());
                }

                @Override
                public void onSuccess(List<String> result) {
                    uv.clearPersons();
                    for (String s : result) {

                        UserWidget userWidget = new UserWidget(s, (lg.equals(s)) ? "images/me_pic.png" : "images/user_male.png", 200);
                        uv.addPerson(userWidget);


                    }
                    mv.addMessage(new MessageWidget("Чат", "images/chat.png", push.getTextMessage(), 43534, "" + (Window.getClientWidth() - 310)));
                    mv.scrollToBottom();
                }
            });
        }
        if (push.getType() == PushType.MESSAGE) {
            mv.addMessage(new MessageWidget(push.getLogin(), (lg.equals(lg)) ? "images/me_pic.png" : "images/user_male.png", push.getTextMessage(), 43534, "" + (Window.getClientWidth() - 310)));
        }
        mv.scrollToBottom();
    }

    public String getSessionId() {
        return sessionId;
    }

    public native String generateSessionId()/*-{
        return "user-" + $wnd.uuidv4();
    }-*/;

    public native void expose()/*-{
        $wnd.exposedMethod = function (param) {
            @com.chat.client.widgets.MainWidget::OnPushReceived(Ljava/lang/String;)(param);
        }
    }-*/;

    public native void connectWS(String sessionId)/*-{
        $wnd.connect(sessionId);
    }-*/;
}
