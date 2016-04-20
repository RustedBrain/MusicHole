package com.rustedbrain.networks.controllers;

import com.rustedbrain.networks.controllers.utils.ChatConnection;
import com.rustedbrain.networks.model.chat.Message;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * Created by RustedBrain on 16.01.2016.
 */
public class ChatController extends Thread {

    private ServerSocket serverSocket;
    private Map<InetAddress, ChatConnection> connections = new HashMap<>();
    private Timer timer = new Timer(true);

    public ChatController() throws IOException {
        serverSocket = ServerSocketFactory.getDefault().createServerSocket();
    }

    public ChatController(int serverPort) throws IOException {
        serverSocket = ServerSocketFactory.getDefault().createServerSocket(serverPort);
    }

    @Override
    public void run() {
        while (!isInterrupted())
            try {
                Socket socket = serverSocket.accept();
                ChatConnection connection = new ChatConnection(socket, this);
                connection.setDaemon(true);
                connection.start();
                connections.put(socket.getInetAddress(), connection);
            } catch (Exception x) {
                x.printStackTrace();
            }
    }

    public void send(Message message, InetAddress address) throws IOException {
        connections.get(address).send(message);
    }

    public void sendToAll(Message message) throws IOException {
        for (ChatConnection connection : this.connections.values()) {
            connection.send(message);
        }
    }

    public void remove(InetAddress inetAddress) {
        connections.remove(inetAddress);
    }

}
