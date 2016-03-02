package com.rustedbrain.networks.utils.logging;

import com.rustedbrain.model.Account;

import javax.net.SocketFactory;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by RustedBrain on 18.01.2016.
 */
public class PasswordVerifier {

    private Socket socket;

    private PasswordVerifier(InetAddress address, int port) throws IOException {
        this.socket = SocketFactory.getDefault().createSocket(address, port);
    }

    public static PasswordVerifier getInstance(String serverName, int port) throws IOException {
        return new PasswordVerifier(InetAddress.getByName(serverName), port);
    }

    public static PasswordVerifier getInstance(InetAddress serverName, int port) throws IOException {
        return new PasswordVerifier(serverName, port);
    }

    public static PasswordVerifier getInstance(String serverName, String port) throws IOException, NumberFormatException {
        return new PasswordVerifier(InetAddress.getByName(serverName), Integer.parseInt(port));
    }

    public Account getProperties(String name, String password) throws IOException, ClassNotFoundException {
        new DataOutputStream(this.socket.getOutputStream()).writeUTF(name + " " + password);
        Account account = (Account) new ObjectInputStream(this.socket.getInputStream()).readObject();
        return account;
    }
}
