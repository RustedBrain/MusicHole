package com.rustedbrain.networks.model.chat;

import com.rustedbrain.networks.model.members.ProxyAccount;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;


public class Message implements Serializable, Comparable<Date> {

    private ProxyAccount account;
    private InetAddress addressSender;
    private InetAddress addressReceiver;
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

    public InetAddress getAddressSender() {
        return addressSender;
    }

    public void setAddressSender(InetAddress addressSender) {
        this.addressSender = addressSender;
    }

    public InetAddress getAddressReceiver() {
        return addressReceiver;
    }

    public void setAddressReceiver(InetAddress addressReceiver) {
        this.addressReceiver = addressReceiver;
    }

    @Override
    public String toString() {
        return "[" + this.date.toString() + "]" + this.account.login + ": " + this.message;
    }
}
