package com.rustedbrain.networks;

import com.rustedbrain.networks.utils.chat.ChatServerHandler;
import com.rustedbrain.networks.utils.logging.PasswordServerHandler;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

public class NetworkApp {

    private static ChatServerHandler chatHandler;
    private static PasswordServerHandler passwordHandler;
    private static SessionFactory factory;

    private static void initBeans() throws IOException {

        chatHandler = new ChatServerHandler(6666);
        factory = new Configuration().configure().buildSessionFactory();
        passwordHandler = new PasswordServerHandler(7777, factory);
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        initBeans();

        chatHandler.start();
        passwordHandler.start();
    }
}
