package com.rustedbrain.networks.model.messages;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by RustedBrain on 18.04.2016.
 */
public class SystemMessage implements Serializable {

    private SystemAction action;
    private InetAddress addressSender;
    private InetAddress addressReceiver;

    public SystemAction getAction() {
        return action;
    }

    public void setAction(SystemAction action) {
        this.action = action;
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
        return "SystemMessage{" +
                "action=" + action +
                ", addressSender=" + addressSender +
                ", addressReceiver=" + addressReceiver +
                '}';
    }
}
