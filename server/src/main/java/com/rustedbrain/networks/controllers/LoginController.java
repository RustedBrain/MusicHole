package com.rustedbrain.networks.controllers;

import com.rustedbrain.networks.model.members.Account;
import com.rustedbrain.networks.model.messages.LoginMessage;
import com.rustedbrain.networks.model.messages.SystemMessage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by RustedBrain on 18.01.2016.
 */
public class LoginController implements Controller {

    private final Map<InetAddress, AppController.Connection> connections;
    private SessionFactory factory;

    public LoginController(Map<InetAddress, AppController.Connection> connections, SessionFactory factory) throws Exception {
        this.factory = factory;
        this.connections = connections;
    }

    private void close() throws IOException {
        this.factory.close();
    }

    @Override
    public void handleMessage(SystemMessage message, Socket clientSocket) {
        LoginMessage loginMessage = (LoginMessage) message;
        System.out.println("Login controller received new message: " + message);
        new LoginHandler(clientSocket, loginMessage, factory.openSession()).start();
    }

    private class LoginHandler extends Thread {

        private final Socket clientSocket;
        private final LoginMessage message;
        private final Session session;

        public LoginHandler(Socket clientSocket, LoginMessage message, Session session) {
            this.clientSocket = clientSocket;
            this.message = message;
            this.session = session;
        }

        public Account getAccount(String name) throws NoSuchElementException {
            Criteria criteria = session.createCriteria(Account.class);
            criteria.add(Restrictions.like("login", name));
            if (!criteria.list().isEmpty())
                return (Account) criteria.list().get(0);
            else
                throw new NoSuchElementException("User with this name not exist!");
        }

        @Override
        public void run() {
            try {
                Account account = getAccount(message.getLogin());
                if (account.getPassword().equals(this.message.getPassword()))
                    LoginController.this.connections.get(clientSocket.getInetAddress()).out.writeObject(account);
                else
                    throw new NoSuchElementException();
            } catch (NoSuchElementException e) {
                try {
                    LoginController.this.connections.get(clientSocket.getInetAddress()).out.writeObject(e);
                    AppController.Connection connection = LoginController.this.connections.get(clientSocket.getInetAddress());
                    connection.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        private void close() {
            this.session.close();
        }
    }
}
