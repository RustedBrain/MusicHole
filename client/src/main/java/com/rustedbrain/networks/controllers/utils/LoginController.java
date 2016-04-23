package com.rustedbrain.networks.controllers.utils;

import com.rustedbrain.networks.model.members.Account;
import com.rustedbrain.networks.model.messages.LoginMessage;
import com.rustedbrain.networks.model.messages.SystemAction;

import javax.net.SocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.NoSuchElementException;

/**
 * Created by RustedBrain on 18.01.2016.
 */
public class LoginController {

    private final Socket socket;

    public LoginController(InetAddress address, int port) throws IOException {
        this.socket = SocketFactory.getDefault().createSocket(address, port);
    }

    public Account getAccount(String login, String password) throws Exception {
        LoginMessage message = prepareLoginMessage(login, password);
        new ObjectOutputStream(this.socket.getOutputStream()).writeObject(message);
        Object object = new ObjectInputStream(this.socket.getInputStream()).readObject();
        if (object instanceof Account)
            return (Account) object;
        else
            throw (NoSuchElementException) object;
    }

    private LoginMessage prepareLoginMessage(String login, String password) {
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setAction(SystemAction.LOGIN);
        loginMessage.setAddressSender(socket.getInetAddress());
        loginMessage.setAddressReceiver(null);
        loginMessage.setLogin(login);
        loginMessage.setPassword(password);
        return loginMessage;
    }
}
