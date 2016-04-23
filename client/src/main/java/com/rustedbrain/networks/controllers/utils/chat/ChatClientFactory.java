package com.rustedbrain.networks.controllers.utils.chat;

import com.rustedbrain.networks.controllers.utils.ChatController;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by RustedBrain on 18.01.2016.
 */
public class ChatClientFactory {

    public static ChatController getChatHandler(String serverName, int port, JList output) throws IOException {
        InetAddress address = InetAddress.getByName(serverName);
        return new ChatController(address, port, output);
    }

    public static ChatController getChatHandler(InetAddress serverName, int port, JList output) throws IOException {
        return new ChatController(serverName, port, output);
    }

    public static ChatController getChatHandler(InetAddress serverName, String port, JList output) throws IOException {
        return new ChatController(serverName, Integer.parseInt(port), output);
    }
}
