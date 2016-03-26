package com.rustedbrain.networks.view.logging;

import com.rustedbrain.networks.model.members.Account;
import com.rustedbrain.networks.utils.logging.PasswordVerifier;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.TransferQueue;

public class MusicHoleLogging extends JDialog {
    public JTextField textFieldServerName;
    public JTextField textFieldServerPort;
    public Account account;
    private TransferQueue<Account> accounts;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldLogin;
    private JPasswordField passwordFieldPassword;
    private JLabel LabelLogin;
    private JLabel LabelPassword;
    private JTabbedPane tabbedPaneCapabilities;
    private JLabel labelServerName;
    private JLabel labelServerPort;

    {
        this.textFieldServerName.setText("127.0.0.1");
        this.textFieldServerPort.setText("7777");
    }

    public MusicHoleLogging() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0)
                , JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            PasswordVerifier verifier = PasswordVerifier.getInstance(this.textFieldServerName.getText(), this.textFieldServerPort.getText());
            this.account = verifier.getAccount(this.textFieldLogin.getText(), new String(this.passwordFieldPassword.getPassword()));
            dispose();
        } catch (IOException | ClassNotFoundException | NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void onCancel() {
        this.account = null;
        dispose();
    }
}
