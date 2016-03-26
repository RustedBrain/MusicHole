package com.rustedbrain.networks.utils.chat;

import com.rustedbrain.networks.model.chat.Message;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by RustedBrain on 16.01.2016.
 */
public class ChatServerHandler extends Thread {

    private ServerSocket serverSocket;
    private Map<InetAddress, Connection> connections = new HashMap<>();
    private Timer timer = new Timer(true);

    public ChatServerHandler() throws IOException {
        serverSocket = ServerSocketFactory.getDefault().createServerSocket();
    }

    public ChatServerHandler(int serverPort) throws IOException {
        serverSocket = ServerSocketFactory.getDefault().createServerSocket(serverPort);
    }

    @Override
    public void run() {
        System.out.println(this.getClass().getSimpleName() + " start to handle connections...");
        while (!isInterrupted())
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, this);
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
        for (Connection connection : this.connections.values()) {
            connection.send(message);
        }
    }

    public void remove(InetAddress inetAddress) {
        connections.remove(inetAddress);
    }

    public void initOnlineStatisticsSender() {
        this.timer.scheduleAtFixedRate(new OnlineStatisticsSender(), 0, TimeUnit.SECONDS.toMillis(60));
    }

    private class OnlineStatisticsSender extends TimerTask {

        @Override
        public void run() {

        }
    }
}
