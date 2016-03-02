package com.rustedbrain.crud.view.dialogs;

import com.rustedbrain.networks.model.music.Album;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AlbumDlg extends JDialog {
    public JTextField textFieldId;
    public JTextField textFieldName;
    public JTextField textFieldTime;
    public JTextField textFieldDescription;
    public JPanel panelFields;
    public JPanel panelButtons;
    public Album album;
    public JLabel labelId;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public AlbumDlg() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> {
            try {
                onOK();
            } catch (Exception e1) {
                AlbumDlg.this.album = null;
                e1.printStackTrace();
                JOptionPane.showMessageDialog(AlbumDlg.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        this.album = new Album();
        this.album.setName(textFieldName.getText());
        this.album.setTime(Double.parseDouble(textFieldTime.getText()));
        this.album.setDescription(textFieldDescription.getText());
        dispose();
    }

    private void onCancel() {
        this.album = null;
        dispose();
    }

    public void fillTextFields(Album album) {
        this.textFieldId.setText(album.getId().toString());
        this.textFieldName.setText(album.getName());
        this.textFieldDescription.setText(album.getDescription());
        this.textFieldTime.setText(album.getTime().toString());
    }
}
