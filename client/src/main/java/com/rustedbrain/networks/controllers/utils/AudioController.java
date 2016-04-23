package com.rustedbrain.networks.controllers.utils;

import com.rustedbrain.networks.model.members.Account;
import com.rustedbrain.networks.model.messages.AudioMessage;
import com.rustedbrain.networks.model.messages.SystemAction;

import javax.net.SocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.NoSuchElementException;

/**
 * Created by RustedBrain on 22.04.2016.
 */
public class AudioController {

    private final Socket socket;

    public AudioController(InetAddress address, int port) throws IOException {
        this.socket = SocketFactory.getDefault().createSocket(address, port);
    }

    public Account getAccount(String login, String password) throws Exception {
        AudioMessage message = prepareLoginMessage(login, password);
        new ObjectOutputStream(this.socket.getOutputStream()).writeObject(message);
        Object object = new ObjectInputStream(this.socket.getInputStream()).readObject();
        if (object instanceof Account)
            return (Account) object;
        else
            throw (NoSuchElementException) object;
    }

    private AudioMessage prepareLoginMessage(String login, String password) {
        AudioMessage audiomessage = new AudioMessage();
        audiomessage.setAction(SystemAction.LOGIN);
        audiomessage.setAddressSender(socket.getInetAddress());
        audiomessage.setAddressReceiver(null);
        return audiomessage;
    }
}
