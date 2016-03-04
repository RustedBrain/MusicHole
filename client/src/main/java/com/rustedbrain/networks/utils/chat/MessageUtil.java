package com.rustedbrain.networks.utils.chat;

import com.rustedbrain.networks.model.chat.Message;
import com.rustedbrain.networks.model.members.Account;

import java.net.InetAddress;
import java.util.Date;

/**
 * Created by RustedBrain on 04.03.2016.
 */
public class MessageUtil {

    private final static String ANONYMOUS_USER = "Anonymous";

    public static void sendToAll(ChatClientHandler chat, boolean isAnonymous, String text, Account myAccount) throws Exception {
        if (isAnonymous) {
            chat.send(createAnonymousMessageToAll(text, InetAddress.getLocalHost()));
        } else chat.send(createMessageToAll(text, myAccount, InetAddress.getLocalHost()));

    }

    public static void sendToAccount(ChatClientHandler chat, boolean isAnonymous, Message messageOld, String text, Account myAccount) throws Exception {
        Message message;
        if (isAnonymous) {
            message = createAnonymousMessageToAll(text, InetAddress.getLocalHost());
        } else message = createMessageToAll(text, myAccount, InetAddress.getLocalHost());
        message.setAddressReceiver(messageOld.getAddressSender());
        chat.send(message);
    }

    public static Message createMessageToAll(String text, Account myAccount, InetAddress addressSender) {
        Message message = prepareMessage(text, addressSender);
        message.setAccount(myAccount);
        return message;
    }

    public static Message createAnonymousMessageToAll(String text, InetAddress addressSender) throws Exception {
        Message message = prepareMessage(text, addressSender);
        Account account = new Account();
        account.setLogin(ANONYMOUS_USER);
        account.setSurname(ANONYMOUS_USER);
        account.setName(ANONYMOUS_USER);
        account.setNationality(ANONYMOUS_USER);
        message.setAccount(account);
        return message;
    }

    private static Message prepareMessage(String text, InetAddress addressSender) {
        Message message = new Message();
        message.setDate(new Date());
        message.setMessage(text);
        message.setAddressSender(addressSender);
        return message;
    }

}
