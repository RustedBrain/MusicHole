package com.rustedbrain.networks;

import com.rustedbrain.networks.controllers.AppController;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class NetworkApp {

    private static AppController appController;
    private static SessionFactory factory;

    private static void initBeans() throws Exception {
        factory = new Configuration().configure().buildSessionFactory();
        appController = new AppController(6666, factory);
    }

    public static void main(String[] args) throws Exception {

        initBeans();
        appController.start();
        System.out.println("EXIT");
    }

    private static String getServerIp() throws IOException {
        URL myIp = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(myIp.openStream()));

        String ip = in.readLine();
        if (ip == null || ip.isEmpty())
            throw new IllegalStateException();
        else return ip;

    }
}
