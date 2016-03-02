package com.rustedbrain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by RustedBrain on 29.02.2016.
 */
public class Message implements Serializable, Comparable<Date> {

    private Account account;
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

    @Override
    public String toString() {
        return "Message{" +
                "account=" + account +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
