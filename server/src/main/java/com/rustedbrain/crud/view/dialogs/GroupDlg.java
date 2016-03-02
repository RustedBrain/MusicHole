package com.rustedbrain.crud.view.dialogs;

import com.rustedbrain.networks.model.music.Group;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;

public class GroupDlg extends JDialog {
    public JTextField textFieldId;
    public JLabel labelId;
    public Group group;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldName;
    private JTextField textFieldNationality;
    private JTextField textFieldCreation;

    public GroupDlg() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> {
            try {
                onOK();
            } catch (Exception e1) {
                this.group = null;
                e1.printStackTrace();
                JOptionPane.showMessageDialog(GroupDlg.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        this.group = new Group();
        this.group.setName(textFieldName.getText());
        this.group.setNationality(textFieldNationality.getText());
        this.group.setCreation(Date.valueOf(textFieldCreation.getText()));
        dispose();
    }

    private void onCancel() {
        this.group = null;
        dispose();
    }

    public void fillTextFields(Group group) {
        this.textFieldId.setText(group.getId().toString());
        this.textFieldName.setText(group.getName());
        this.textFieldCreation.setText(group.getCreation().toString());
        this.textFieldNationality.setText(group.getNationality());
    }
}
