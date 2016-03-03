package com.rustedbrain.networks.model.chat;

import com.rustedbrain.networks.model.members.Account;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;


public class Message implements Serializable, Comparable<Date> {

    private Account account;
    private InetAddress inetAddress;
    private String message;
    private Date date;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
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

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInet6Address(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    @Override
    public String toString() {
        return "Message{" +
                "account=" + account +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
