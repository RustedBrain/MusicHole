package com.rustedbrain.networks.view;

import com.rustedbrain.networks.model.chat.Message;
import com.rustedbrain.networks.model.members.Account;
import com.rustedbrain.networks.utils.chat.ChatClientFactory;
import com.rustedbrain.networks.utils.chat.ChatClientHandler;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Date;

public class MusicHoleMainWindow extends JDialog {
    private JPanel contentPane;
    private JTabbedPane tabbedPaneMenus;
    private JLabel labelLogin;
    private JLabel labelName;
    private JLabel labelSurname;
    private JLabel labelMail;
    private JList listPlayList;
    private JTextField textFieldChatAnswer;
    private JButton buttonSend;
    private JTextArea textAreaChatMessages;
    private JButton buttonPlay;
    private JButton buttonDownload;
    private JProgressBar progressBar1;
    private JTextField textFieldLogin;
    private JTextField textFieldName;
    private JTextField textFieldSurname;
    private JTextField textFieldMail;
    private JTabbedPane tabbedPaneOptions;
    private JRadioButton radioButtonAnonymous;
    private JButton buttonReconnect;
    private JTextField textFieldPort;
    private JTextField textFieldServerName;
    private JLabel labelServerName;
    private JLabel labelServerPort;
    private JLabel labelNationality;
    private JTextField textFieldNationality;
    private JLabel labelBirthday;
    private JTextField textFieldBirthday;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JLabel labelGenre;
    private JLabel labelGroup;
    private JLabel labelAlbum;
    private Account account;
    private ChatClientHandler chat;

    {
        try {
            textFieldServerName.setText("127.0.0.1");
            textFieldPort.setText("6666");
            radioButtonAnonymous.setSelected(false);
            chat = ChatClientFactory.getChatHandler(textFieldServerName.getText()
                    , Integer.parseInt(textFieldPort.getText())
                    , textAreaChatMessages);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Server is not available");
        }
        chat.start();
    }

    public MusicHoleMainWindow() {
        setContentPane(contentPane);
        setModal(true);

        buttonSend.addActionListener(e -> onSend());


        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });


        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0)
                , JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        buttonReconnect.addActionListener(e -> {
            try {
                chat.close();
                chat = ChatClientFactory.getChatHandler(textFieldServerName.getText()
                        , Integer.parseInt(textFieldPort.getText())
                        , textAreaChatMessages);
                JOptionPane.showMessageDialog(MusicHoleMainWindow.this, "Successful");
            } catch (IOException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(MusicHoleMainWindow.this, "Server is not available");
            }
        });
    }

    public MusicHoleMainWindow(Account account) {
        this();
        this.account = account;
        setAccountInfo(account);
        this.pack();
        this.setVisible(true);
    }

    private void setAccountInfo(Account account) {
        this.textFieldLogin.setText(account.getLogin());
        this.textFieldName.setText(account.getName());
        this.textFieldSurname.setText(account.getSurname());
        this.textFieldNationality.setText(account.getNationality());
        this.textFieldMail.setText(account.getMail());
        this.textFieldBirthday.setText(account.getBirthday().toString());
    }

    private void onSend() {
        try {
            Message message = new Message();
            message.setAccount(this.account);
            message.setDate(new Date());
            message.setMessage(this.textFieldChatAnswer.getText());
            chat.send(message);
            this.textFieldChatAnswer.setText(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onClose() {
        chat.close();
        dispose();
    }
}
