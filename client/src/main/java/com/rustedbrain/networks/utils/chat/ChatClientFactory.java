package com.rustedbrain.networks.utils.chat;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by RustedBrain on 18.01.2016.
 */
public class ChatClientFactory {

    public static ChatClientHandler getChatHandler(String serverName, int port, JEditorPane output) throws IOException {
        InetAddress address = InetAddress.getByName(serverName);
        return new ChatClientHandler(address, port, output);
    }

    public static ChatClientHandler getChatHandler(InetAddress serverName, int port, JEditorPane output) throws IOException {
        return new ChatClientHandler(serverName, port, output);
    }

    public static ChatClientHandler getChatHandler(InetAddress serverName, String port, JEditorPane output) throws IOException {
        return new ChatClientHandler(serverName, Integer.parseInt(port), output);
    }
}
