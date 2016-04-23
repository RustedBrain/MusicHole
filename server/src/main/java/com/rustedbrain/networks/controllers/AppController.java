package com.rustedbrain.networks.controllers;

import com.rustedbrain.networks.model.messages.SystemMessage;
import org.hibernate.SessionFactory;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by RustedBrain on 16.01.2016.
 */
public final class AppController extends Thread {

    private ServerSocket serverSocket;
    private Map<InetAddress, Connection> connections = new HashMap<>();
    private SessionFactory factory;
    private ChatController chatController;
    private LoginController loginController;
    private LogoutController logoutController;
    private AudioController audioController;

    public AppController(SessionFactory factory) throws Exception {
        this.factory = factory;
        serverSocket = ServerSocketFactory.getDefault().createServerSocket();
        initControllers();
    }

    public AppController(int serverPort, SessionFactory factory) throws Exception {
        this.factory = factory;
        serverSocket = ServerSocketFactory.getDefault().createServerSocket(serverPort);
        initControllers();
    }

    private void initControllers() throws Exception {
        this.loginController = new LoginController(connections, factory);
        this.logoutController = new LogoutController(connections);
        this.chatController = new ChatController(connections);
        this.audioController = new AudioController(connections, factory);
    }

    @Override
    public void run() {
        while (!isInterrupted())
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                connection.setDaemon(true);
                connection.start();
                connections.put(socket.getInetAddress(), connection);
            } catch (Exception x) {
                x.printStackTrace();
            }
    }

    public void remove(InetAddress inetAddress) {
        connections.remove(inetAddress);
    }

    public class Connection extends Thread {

        public ObjectOutputStream out;
        private ObjectInputStream in;
        private Socket clientSocket;

        public Connection(Socket aClientSocket) {
            try {
                clientSocket = aClientSocket;
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    SystemMessage message = (SystemMessage) in.readObject();
                    System.out.println("App controller received new message: " + message.toString());
                    switch (message.getAction()) {

                        case LOGIN:
                            AppController.this.loginController.handleMessage(message, clientSocket);
                            break;
                        case CHAT:
                            AppController.this.chatController.handleMessage(message, clientSocket);
                            break;
                        case MUSIC:
                            AppController.this.audioController.handleMessage(message, clientSocket);
                            break;
                        case LOGOUT:
                            AppController.this.logoutController.handleMessage(message, clientSocket);
                            break;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    this.interrupt();
                    e.printStackTrace();
                    try {
                        this.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }

        public void close() throws IOException {
            out.close();
            in.close();
            clientSocket.close();
            AppController.this.remove(this.clientSocket.getInetAddress());
            this.interrupt();
            System.out.println(this.getClass().getSimpleName() + ": " + clientSocket.getInetAddress() + " closed");
        }

    }
}
