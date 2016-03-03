package com.rustedbrain.networks.utils.chat;


import com.rustedbrain.networks.model.chat.Message;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by RustedBrain on 16.01.2016.
 */
public class ChatClientHandler extends Thread {

    public Socket socket;
    private Thread receiver;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Integer messagesCount;
    private JList textComponent;

    ChatClientHandler(InetAddress serverName, int serverPort, JList textComponent) throws IOException {
        this(serverName, serverPort, 200, textComponent);
    }

    ChatClientHandler(InetAddress serverName, int serverPort, Integer messagesCount, JList textComponent) throws IOException {
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

    public void send(Message message) throws IOException {
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

    private void addMessage(Message message) {
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

    public Message getMessage(int index) {
        DefaultListModel model = (DefaultListModel) textComponent.getModel();
        return (Message) model.get(index);
    }

    private class MessageReceiver extends Thread {

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Message message = (Message) ChatClientHandler.this.in.readObject();
                    ChatClientHandler.this.addMessage(message);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    this.interrupt();
                }
            }
        }
    }
}