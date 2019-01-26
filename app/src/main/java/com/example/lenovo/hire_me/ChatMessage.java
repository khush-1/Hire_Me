package com.example.lenovo.hire_me;

import java.util.Date;

public class ChatMessage {
    private String messagetext,messageuser;
    private long messagetime;

    public ChatMessage(String messagetext, String messageuser) {
        this.messagetext = messagetext;
        this.messageuser = messageuser;
        messagetime = new Date().getTime();

    }
    public ChatMessage()
    {

    }

    public String getMessagetext() {
        return messagetext;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }

    public String getMessageuser() {
        return messageuser;
    }

    public void setMessageuser(String messageuser) {
        this.messageuser = messageuser;
    }

    public long getMessagetime() {
        return messagetime;
    }

    public void setMessagetime(long messagetime) {
        this.messagetime = messagetime;
    }
}
