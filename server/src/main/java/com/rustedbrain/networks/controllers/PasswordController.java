package com.rustedbrain.networks.controllers;

import com.rustedbrain.networks.model.members.Account;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.net.ServerSocketFactory;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;

/**
 * Created by RustedBrain on 18.01.2016.
 */
public class PasswordController extends Thread {

    private Session session;
    private ServerSocket serverSocket;

    public PasswordController(String serverIp, int serverPort, SessionFactory factory) throws IOException {
        this.serverSocket = ServerSocketFactory.getDefault().createServerSocket(serverPort);
        this.session = factory.openSession();
    }

    @Override
    public void run() {
        System.out.println(this.getClass().getSimpleName() + " start to handle connections...");
        while (!isInterrupted())
            try {
                Socket client = serverSocket.accept();
                new PasswordVerifier(client).start();
            } catch (Exception x) {
                x.printStackTrace();
            }
        try {
            this.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() throws IOException {
        this.session.close();
        this.serverSocket.close();
    }

    public Account getAccount(String name) {
        Criteria criteria = session.createCriteria(Account.class);
        criteria.add(Restrictions.like("login", name));
        if (!criteria.list().isEmpty())
            return (Account) criteria.list().get(0);
        else throw new NoSuchElementException("User with this name not exist!");
    }

    private class PasswordVerifier extends Thread {

        private final Socket client;
        private DataInputStream in;
        private ObjectOutputStream out;

        public PasswordVerifier(Socket client) throws IOException {
            this.client = client;
            this.out = new ObjectOutputStream(client.getOutputStream());
            this.in = new DataInputStream(client.getInputStream());
        }

        @Override
        public void run() {
            try {
                String[] loginPassword = in.readUTF().split(" ");
                Account account = getAccount(loginPassword[0]);
                if (account.getPassword().equals(loginPassword[1])) {
                    out.writeObject(account);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    this.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void close() throws IOException {
            in.close();
            out.close();
            client.close();
        }
    }
}