package com.rustedbrain.networks.model.messages;

public class LoginMessage extends SystemMessage {

    private String login;
    private String password;

    public LoginMessage(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LoginMessage() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginMessage{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
