package com.rustedbrain.networks.view;

import com.rustedbrain.networks.model.members.ProxyAccount;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AccountInfoWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldLogin;
    private JTextField textFieldName;
    private JTextField textFieldSurname;
    private JTextField textFieldNationality;
    private JTextField textFieldMail;
    private JTextField textFieldBirthday;
    private JTextField textFieldRegistration;

    public AccountInfoWindow() {
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

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public void fillAccountFields(ProxyAccount account) {
        this.textFieldLogin.setText(account.login);
        this.textFieldName.setText(account.name);
        this.textFieldSurname.setText(account.surname);
        this.textFieldMail.setText(account.mail);
        this.textFieldNationality.setText(account.nationality);
        this.textFieldBirthday.setText(account.birthday.toString());
        this.textFieldRegistration.setText(account.registration.toString());
    }
}
