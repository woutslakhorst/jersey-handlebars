package com.nedap.healthcare;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wout.slakhorst on 10/07/15.
 */
@XmlRootElement
public class Greeting {
    private String message = "Hello world";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
