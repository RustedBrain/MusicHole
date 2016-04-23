package com.rustedbrain.networks.controllers.utils.chat;

import com.rustedbrain.networks.controllers.utils.ChatController;
import com.rustedbrain.networks.model.members.Account;
import com.rustedbrain.networks.model.members.ProxyAccount;
import com.rustedbrain.networks.model.messages.ChatMessage;
import com.rustedbrain.networks.model.messages.SystemAction;

import java.net.InetAddress;
import java.util.Date;

/**
 * Created by RustedBrain on 04.03.2016.
 */
public class MessageUtil {

    private final static String ANONYMOUS_USER = "Anonymous";

    public static void sendToAll(ChatController chat, boolean isAnonymous, String text, ProxyAccount myAccount) throws Exception {
        if (isAnonymous) {
            chat.send(createAnonymousMessageToAll(text, InetAddress.getLocalHost()));
        } else
            chat.send(createMessageToAll(text, myAccount, InetAddress.getLocalHost()));

    }

    public static void sendToAccount(ChatController chat, boolean isAnonymous, ChatMessage messageOld, String text, ProxyAccount myAccount) throws Exception {
        ChatMessage message;
        if (isAnonymous) {
            message = createAnonymousMessageToAll(text, InetAddress.getLocalHost());
        } else message = createMessageToAll(text, myAccount, InetAddress.getLocalHost());
        message.setAddressReceiver(messageOld.getAddressSender());
        chat.send(message);
    }

    public static ChatMessage createMessageToAll(String text, ProxyAccount myAccount, InetAddress addressSender) {
        ChatMessage message = prepareMessage(text, addressSender);
        message.setAccount(myAccount);
        return message;
    }

    public static ChatMessage createAnonymousMessageToAll(String text, InetAddress addressSender) throws Exception {
        ChatMessage message = prepareMessage(text, addressSender);
        ProxyAccount account = new ProxyAccount();
        account.login = ANONYMOUS_USER;
        account.surname = ANONYMOUS_USER;
        account.name = ANONYMOUS_USER;
        account.nationality = ANONYMOUS_USER;
        message.setAccount(account);
        return message;
    }

    private static ChatMessage prepareMessage(String text, InetAddress addressSender) {
        ChatMessage message = new ChatMessage();
        message.setAction(SystemAction.CHAT);
        message.setDate(new Date());
        message.setMessage(text);
        message.setAddressSender(addressSender);
        return message;
    }

    public static ProxyAccount createProxyAccount(Account myAccount) {
        ProxyAccount account = new ProxyAccount();
        account.login = myAccount.getLogin();
        account.name = myAccount.getLogin();
        account.surname = myAccount.getLogin();
        account.registration = myAccount.getRegistration();
        account.nationality = myAccount.getNationality();
        account.birthday = myAccount.getBirthday();
        account.mail = myAccount.getMail();
        return account;
    }

}
