package com.rustedbrain.networks.controllers;

import com.rustedbrain.networks.model.messages.SystemMessage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

/**
 * Created by RustedBrain on 22.04.2016.
 */
public class LogoutController implements Controller {

    private final Map<InetAddress, AppController.Connection> connections;

    public LogoutController(Map<InetAddress, AppController.Connection> connections) {
        this.connections = connections;
    }

    @Override
    public void handleMessage(SystemMessage message, Socket clientSocket) {
        System.out.println("Logout controller received new message: " + message);
        try {
            connections.get(clientSocket.getInetAddress()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
