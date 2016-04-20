package com.rustedbrain.networks.controllers.utils;

import com.rustedbrain.networks.controllers.ChatController;
import com.rustedbrain.networks.model.chat.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by RustedBrain on 18.04.2016.
 */
public class ChatConnection extends Thread {

    public ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket clientSocket;
    private ChatController chat;

    public ChatConnection(Socket aClientSocket, ChatController chat) {
        try {
            clientSocket = aClientSocket;
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            this.chat = chat;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Message message = (Message) in.readObject();
                if (message.getAddressReceiver() != null)
                    chat.send(message, message.getAddressReceiver());
                else
                    chat.sendToAll(message);
            } catch (IOException e) {
                this.interrupt();
                e.printStackTrace();
                try {
                    this.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(Message message) throws IOException {
        this.out.writeObject(message);
        this.out.flush();
    }

    public void close() throws IOException {
        out.close();
        in.close();
        clientSocket.close();
        chat.remove(this.clientSocket.getInetAddress());
    }

}