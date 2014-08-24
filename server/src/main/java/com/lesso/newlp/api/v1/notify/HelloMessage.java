package com.lesso.newlp.api.v1.notify;

/**
 * Created by Sean on 4/13/2014.
 */
public class HelloMessage {

//    private String name;
    private String type;
    private int callback_id;

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCallback_id() {
        return callback_id;
    }

    public void setCallback_id(int callback_id) {
        this.callback_id = callback_id;
    }
}