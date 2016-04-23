package com.rustedbrain.networks.controllers.utils;

import com.rustedbrain.networks.model.messages.SystemAction;
import com.rustedbrain.networks.model.messages.SystemMessage;

import javax.net.SocketFactory;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by RustedBrain on 22.04.2016.
 */
public class LogoutController {

    private final Socket socket;

    public LogoutController(InetAddress address, int port) throws IOException {
        this.socket = SocketFactory.getDefault().createSocket(address, port);
    }

    public void notifyServer() throws IOException {
        new ObjectOutputStream(this.socket.getOutputStream()).writeObject(prepareMessage());
    }

    private SystemMessage prepareMessage() {
        SystemMessage message = new SystemMessage();
        message.setAction(SystemAction.LOGOUT);
        return message;
    }
}
