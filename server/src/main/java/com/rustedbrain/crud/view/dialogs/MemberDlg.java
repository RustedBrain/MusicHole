package com.rustedbrain.crud.view.dialogs;

import com.rustedbrain.networks.model.music.Member;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;

public class MemberDlg extends JDialog {
    public Member member;
    public JTextField textFieldId;
    public JLabel labelId;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldPseudonym;
    private JTextField textFieldName;
    private JTextField textFieldPatronymic;
    private JTextField textFieldSurname;
    private JTextField textFieldNationality;
    private JTextField textFieldBirthday;

    public MemberDlg() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> {
            try {
                onOK();
            } catch (Exception e1) {
                MemberDlg.this.member = null;
                e1.printStackTrace();
                JOptionPane.showMessageDialog(MemberDlg.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        this.member = new Member();
        this.member.setName(textFieldName.getText());
        this.member.setBirthday(Date.valueOf(textFieldBirthday.getText()));
        this.member.setNationality(textFieldNationality.getText());
        this.member.setPatronymic(textFieldPatronymic.getText());
        this.member.setPseudonym(textFieldPseudonym.getText());
        this.member.setSurname(textFieldSurname.getText());
        dispose();
    }

    private void onCancel() {
        this.member = null;
        dispose();
    }

    public void fillTextFields(Member member) {
        this.textFieldId.setText(member.getId().toString());
        this.textFieldName.setText(member.getName());
        this.textFieldBirthday.setText(member.getBirthday().toString());
        this.textFieldNationality.setText(member.getNationality());
        this.textFieldPatronymic.setText(member.getPatronymic());
        this.textFieldPseudonym.setText(member.getPseudonym());
        this.textFieldSurname.setText(member.getSurname());
    }
}
