package com.chat.client.widgets.uibinderversion;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface CssRes extends ClientBundle {

    public interface MyCss extends CssResource {
        String users();

        String loginPanel();


    }

    @Source("uibinder.css")
    MyCss style();
}

