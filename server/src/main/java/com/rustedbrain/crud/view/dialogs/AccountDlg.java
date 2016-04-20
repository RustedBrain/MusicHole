package com.rustedbrain.crud.view.dialogs;

import com.rustedbrain.networks.controllers.utils.Validator;
import com.rustedbrain.networks.model.members.Account;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;

public class AccountDlg extends JDialog {
    public Account account;
    public JTextField textFieldId;
    public JLabel labelId;
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
    private JTextField textFieldPassword;

    public AccountDlg() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> {
            try {
                onOK();
            } catch (Exception e1) {
                AccountDlg.this.account = null;
                e1.printStackTrace();
                JOptionPane.showMessageDialog(AccountDlg.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() throws Exception {
        this.account = new Account();
        this.account.setSurname(textFieldSurname.getText());
        this.account.setNationality(textFieldNationality.getText());
        this.account.setBirthday(Date.valueOf(textFieldBirthday.getText()));
        this.account.setLogin(Validator.LOGIN.validate(textFieldLogin.getText()));
        this.account.setMail(Validator.EMAIL.validate(textFieldMail.getText()));
        this.account.setName(Validator.NAME.validate(textFieldName.getText()));
        this.account.setPassword(Validator.PASSWORD.validate(textFieldPassword.getText()));
        this.account.setRegistration(Date.valueOf(textFieldRegistration.getText()));
        dispose();
    }

    private void onCancel() {
        this.account = null;
        dispose();
    }

    public void fillTextFields(Account account) {
        this.textFieldId.setText(account.getId().toString());
        this.textFieldName.setText(account.getName());
        this.textFieldBirthday.setText(account.getBirthday().toString());
        this.textFieldLogin.setText(account.getLogin());
        this.textFieldMail.setText(account.getMail());
        this.textFieldNationality.setText(account.getNationality());
        this.textFieldPassword.setText(account.getPassword());
        this.textFieldRegistration.setText(account.getRegistration().toString());
        this.textFieldSurname.setText(account.getSurname());
    }
}
