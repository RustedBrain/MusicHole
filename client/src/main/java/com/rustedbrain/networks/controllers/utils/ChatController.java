package com.rustedbrain.networks.controllers.utils;


import com.rustedbrain.networks.model.messages.ChatMessage;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by RustedBrain on 16.01.2016.
 */
public class ChatController extends Thread {

    public Socket socket;
    private Thread receiver;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Integer messagesCount;
    private JList textComponent;

    public ChatController(InetAddress serverName, int serverPort, JList textComponent) throws IOException {
        this(serverName, serverPort, 200, textComponent);
    }

    ChatController(InetAddress serverName, int serverPort, Integer messagesCount, JList textComponent) throws IOException {
        socket = new Socket(serverName, serverPort);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        this.textComponent = textComponent;
        this.messagesCount = messagesCount;
        receiver = new MessageReceiver();
        receiver.start();
    }

    public void setTextComponent(JList textComponent) {
        this.textComponent = textComponent;
    }

    public void send(ChatMessage message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    public void close() {
        try {
            receiver.interrupt();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMessage(ChatMessage message) {
        if (textComponent.getModel().getSize() * 2 > messagesCount) {
            DefaultListModel model = (DefaultListModel) textComponent.getModel();
            model.ensureCapacity(messagesCount);
        }
        if (this.textComponent != null) {
            DefaultListModel model = (DefaultListModel) this.textComponent.getModel();
            model.addElement(message);
        }
    }

    private void clearMessages() {
        DefaultListModel model = (DefaultListModel) textComponent.getModel();
        model.clear();
    }

    public ChatMessage getMessage(int index) {
        DefaultListModel model = (DefaultListModel) textComponent.getModel();
        return (ChatMessage) model.get(index);
    }

    private class MessageReceiver extends Thread {

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    ChatMessage message = (ChatMessage) ChatController.this.in.readObject();
                    ChatController.this.addMessage(message);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    this.interrupt();
                }
            }
        }
    }
}