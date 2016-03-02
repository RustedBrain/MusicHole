package com.rustedbrain.networks.utils.chat;

import com.rustedbrain.model.Message;

import javax.swing.text.JTextComponent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by RustedBrain on 16.01.2016.
 */
public class ChatClientHandler extends Thread {

    private Thread receiver;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    private Map<Date, Message> messageMap;
    private Integer messagesCount;
    private JTextComponent textComponent;

    ChatClientHandler(InetAddress serverName, int serverPort, JTextComponent textComponent) throws IOException {
        this(serverName, serverPort, 200, textComponent);
    }

    ChatClientHandler(InetAddress serverName, int serverPort, Integer messagesCount, JTextComponent textComponent) throws IOException {
        socket = new Socket(serverName, serverPort); // создаем сокет используя IP-адрес и порт сервера.
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        this.textComponent = textComponent;
        this.messagesCount = messagesCount;
        receiver = new MessageReceiver();
        receiver.start();
    }

    public void setTextComponent(JTextComponent textComponent) {
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
        if (this.messageMap == null || (messagesCount * 2) < this.messageMap.size())
            this.messageMap = new TreeMap<>();
        this.messageMap.put(message.getDate(), message);
        if (this.textComponent != null) {
            StringBuilder stringBuilder = new StringBuilder(10);
            stringBuilder.append("[").append(message.getDate().toString()).append("]")
                    .append(" ").append(message.getAccount().getLogin()).append(":").append(message.getMessage());
            this.textComponent.setText(this.textComponent.getText() + "\n" + stringBuilder.toString());
        }
    }

    private void clearMessages() {
        this.messageMap.clear();
    }

    public Message getMessage(Date date) {
        return this.messageMap.get(date);
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