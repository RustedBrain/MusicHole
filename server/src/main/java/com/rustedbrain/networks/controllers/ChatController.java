package com.rustedbrain.networks.controllers;

import com.rustedbrain.networks.model.messages.ChatMessage;
import com.rustedbrain.networks.model.messages.SystemMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

/**
 * Created by RustedBrain on 22.04.2016.
 */
public class ChatController implements Controller {

    private final Map<InetAddress, AppController.Connection> connections;

    public ChatController(Map<InetAddress, AppController.Connection> connections) {
        this.connections = connections;
    }

    @Override
    public void handleMessage(SystemMessage message, Socket clientSocket) {
        ChatMessage chatMessage = (ChatMessage) message;
        System.out.println("Chat controller received new message: " + message);
        new MessageHandler(chatMessage);
    }

    private class MessageHandler extends Thread {

        private final ChatMessage message;

        private MessageHandler(ChatMessage message) {
            this.message = message;
            this.setDaemon(true);
            this.start();
        }

        @Override
        public void run() {
            try {
                if (message.getAddressReceiver() != null)
                    sendMessage(message, message.getAddressReceiver());
                else
                    sendMessageToAll(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(ChatMessage message, InetAddress address) throws IOException {
            send(connections.get(address).out, message);
        }

        public void sendMessageToAll(ChatMessage message) throws IOException {
            for (AppController.Connection connection : connections.values()) {
                send(connection.out, message);
            }
        }

        public void send(ObjectOutputStream out, ChatMessage message) throws IOException {
            out.writeObject(message);
            out.flush();
        }
    }

}
