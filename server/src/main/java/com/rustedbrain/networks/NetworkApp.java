package com.rustedbrain.networks;

import com.rustedbrain.networks.controllers.AudioController;
import com.rustedbrain.networks.controllers.ChatController;
import com.rustedbrain.networks.controllers.PasswordController;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class NetworkApp {

    private static ChatController chatController;
    private static PasswordController passwordController;
    private static AudioController audioController;
    private static SessionFactory factory;

    private static void initBeans() throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

        String ip = in.readLine();
        if (ip == null || ip.isEmpty())
            throw new IllegalStateException();

        factory = new Configuration().configure().buildSessionFactory();
        chatController = new ChatController(6666);
        passwordController = new PasswordController(ip, 7777, factory);
        //audioController = new AudioController(8888);
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        initBeans();

        chatController.start();
        passwordController.start();
        audioController.start();
    }
}
