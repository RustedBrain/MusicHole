package com.rustedbrain.networks.view;

import com.rustedbrain.networks.controllers.utils.AudioController;
import com.rustedbrain.networks.controllers.utils.ChatController;
import com.rustedbrain.networks.controllers.utils.LogoutController;
import com.rustedbrain.networks.controllers.utils.chat.ChatClientFactory;
import com.rustedbrain.networks.controllers.utils.chat.MessageUtil;
import com.rustedbrain.networks.model.members.Account;
import com.rustedbrain.networks.model.members.ProxyAccount;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;

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
    private JList listChat;
    private JList listOnlineUsers;
    private JEditorPane editorPaneInfo;
    private JButton buttonChoice;
    private JButton buttonBack;
    private JButton refreshButton;
    private Account account;
    private ChatController chatController;
    private AccountInfoWindow accountInfoWindow = new AccountInfoWindow();
    private JPopupMenu menu = new JPopupMenu();
    private LogoutController logoutController;
    private AudioController audioController;

    {
        popupMenuInit();
        listPlayListInit();

        try {
            controllersInit();
            chatController.start();
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
                onButtonSendClicked();
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(MusicHoleMainWindow.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    MusicHoleMainWindow.this.onClose();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });


        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0)
                , JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        buttonReconnect.addActionListener(e -> {
            try {
                chatController.close();
                chatController = ChatClientFactory.getChatHandler(textFieldServerName.getText()
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
                                          if (SwingUtilities.isRightMouseButton(evt) && !listChat.isSelectionEmpty()) {
                                              int index = list.locationToIndex(evt.getPoint());
                                              menu.show(listChat, evt.getX(), evt.getY());
                                          }
                                      }
                                  }
        );
        textFieldChatAnswer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    try {
                        onButtonSendClicked();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(MusicHoleMainWindow.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
            }
        });

        tabbedPaneMenus.addChangeListener(e -> {
            JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
            int index = sourceTabbedPane.getSelectedIndex();
            if (sourceTabbedPane.getTitleAt(index).equalsIgnoreCase("music")) {
                System.out.println(sourceTabbedPane.getTitleAt(index));
                try {

                    audioController.getAccount(null, null);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.out.println(1);
            }
        });
    }

    public MusicHoleMainWindow(Account account) {
        this();
        this.account = account;
        setAccountInfo(account);
        this.setLocationRelativeTo(null);
    }

    private void listPlayListInit() {
        DefaultListModel listModel = new DefaultListModel();
        listPlayList.setModel(listModel);
    }

    private void onButtonSendClicked() throws Exception {
        if (textFieldChatAnswer.getText() != null && !textFieldChatAnswer.getText().equals("")) {
            ProxyAccount account = MessageUtil.createProxyAccount(MusicHoleMainWindow.this.account);
            MessageUtil.sendToAll(chatController, this.radioButtonAnonymous.isSelected(), textFieldChatAnswer.getText(), account);
            textFieldChatAnswer.setText(null);
        }
    }

    private void popupMenuInit() {
        JMenuItem item = new JMenuItem("Info");
        item.addActionListener(e -> {
            ProxyAccount proxyAccount = this.chatController.getMessage(this.listChat.getSelectedIndex()).getAccount();
            MusicHoleMainWindow.this.accountInfoWindow.fillAccountFields(proxyAccount);
            MusicHoleMainWindow.this.accountInfoWindow.pack();
            ViewUtil.centerWindow(MusicHoleMainWindow.this.accountInfoWindow);
            MusicHoleMainWindow.this.accountInfoWindow.setVisible(true);
        });
        menu.add(item);
    }


    private void controllersInit() throws IOException {
        String serverHost = "127.0.0.1";
        String serverPort = "6666";
        DefaultListModel model = new DefaultListModel();
        this.listChat.setModel(model);
        textFieldServerName.setText(serverHost);
        textFieldPort.setText(serverPort);
        chatController = ChatClientFactory.getChatHandler(InetAddress.getByName(serverHost), Integer.parseInt(serverPort), listChat);
        System.out.println("Chat controller ready");
        logoutController = new LogoutController(InetAddress.getByName(serverHost), Integer.parseInt(serverPort));
        System.out.println("Logout controller ready");
        audioController = new AudioController(InetAddress.getByName(serverHost), Integer.parseInt(serverPort));
        System.out.println("Audio controller ready");
    }

    private void setAccountInfo(Account account) {
        this.textFieldLogin.setText(account.getLogin());
        this.textFieldName.setText(account.getName());
        this.textFieldSurname.setText(account.getSurname());
        this.textFieldNationality.setText(account.getNationality());
        this.textFieldMail.setText(account.getMail());
        this.textFieldBirthday.setText(account.getBirthday().toString());
    }

    public void onClose() throws IOException {
        this.logoutController.notifyServer();
        chatController.close();
        dispose();
    }
}