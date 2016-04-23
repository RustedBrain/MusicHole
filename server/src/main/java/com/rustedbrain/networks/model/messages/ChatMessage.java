package com.rustedbrain.networks.model.messages;

import com.rustedbrain.networks.model.members.ProxyAccount;

import java.util.Date;


public class ChatMessage extends SystemMessage implements Comparable<Date> {

    private ProxyAccount account;
    private String message;
    private Date date;

    public ProxyAccount getAccount() {
        return account;
    }

    public void setAccount(ProxyAccount account) {
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Date o) {
        return this.date.compareTo(o);
    }

    @Override
    public String toString() {
        return "[Account: " + this.account + " date: " + this.date + "]:" + message;
    }
}
