package com.rustedbrain.networks.view;

import com.rustedbrain.networks.model.members.Account;
import com.rustedbrain.networks.model.members.ProxyAccount;
import com.rustedbrain.networks.utils.chat.ChatClientFactory;
import com.rustedbrain.networks.utils.chat.ChatClientHandler;
import com.rustedbrain.networks.utils.chat.MessageUtil;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class MusicHoleMainWindow extends JDialog {
    public JPopupMenu popup;
    private JPanel contentPane;
    private JTabbedPane tabbedPaneMenus;
    private JLabel labelLogin;
    private JLabel labelName;
    private JLabel labelSurname;
    private JLabel labelMail;
    private JList listPlayList;
    private JTextField textFieldChatAnswer;
    private JButton buttonSend;
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
    private JList listChat;
    private Account account;
    private MessageUtil messageUtil;
    private ChatClientHandler chat;
    private AccountInfoWindow accountInfoWindow = new AccountInfoWindow();

    {
        try {
            chatInit();
            chat.start();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Server is not available");
        }
    }

    public MusicHoleMainWindow() {
        setContentPane(contentPane);
        setModal(true);

        buttonSend.addActionListener(e -> {
            try {
                if (textFieldChatAnswer.getText() != null && !textFieldChatAnswer.getText().equals("")) {
                    ProxyAccount account = messageUtil.createProxyAccount(MusicHoleMainWindow.this.account);
                    MessageUtil.sendToAll(chat, this.radioButtonAnonymous.isSelected(), textFieldChatAnswer.getText(), account);
                }
                textFieldChatAnswer.setText(null);
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(MusicHoleMainWindow.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


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
                        , listChat);
                JOptionPane.showMessageDialog(MusicHoleMainWindow.this, "Successful");
            } catch (IOException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(MusicHoleMainWindow.this, "Server is not available");
            }
        });

        listChat.addMouseListener(new MouseAdapter() {
                                      public void mouseClicked(MouseEvent evt) {
                                          JList list = (JList) evt.getSource();
                                          if (evt.getClickCount() == 2 && !listChat.isSelectionEmpty()) {
                                              int index = list.locationToIndex(evt.getPoint());
                                          }
                                      }
                                  }
        );
    }

    public MusicHoleMainWindow(Account account) {
        this();
        this.account = account;
        setAccountInfo(account);
        this.setLocationRelativeTo(null);
    }

    private void chatInit() throws IOException {
        DefaultListModel model = new DefaultListModel();
        this.listChat.setModel(model);

        JPopupMenu menu = new JPopupMenu();

        JMenuItem item = new JMenuItem("1.Show info");
        item.addActionListener(e -> {
            ProxyAccount proxyAccount = this.chat.getMessage(this.listChat.getSelectedIndex()).getAccount();
            MusicHoleMainWindow.this.accountInfoWindow.fillAccountFields(proxyAccount);
        });


        menu.add(item);

        listChat.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup.show(e.getComponent(),
                            e.getX(), e.getY());
                }
            }
        });

        textFieldServerName.setText("127.0.0.1");
        textFieldPort.setText("6666");
        chat = ChatClientFactory.getChatHandler(
                textFieldServerName.getText()
                , Integer.parseInt(textFieldPort.getText())
                , listChat);
    }

    private void setAccountInfo(Account account) {
        this.textFieldLogin.setText(account.getLogin());
        this.textFieldName.setText(account.getName());
        this.textFieldSurname.setText(account.getSurname());
        this.textFieldNationality.setText(account.getNationality());
        this.textFieldMail.setText(account.getMail());
        this.textFieldBirthday.setText(account.getBirthday().toString());
    }

    private void onClose() {
        chat.close();
        dispose();
    }

}