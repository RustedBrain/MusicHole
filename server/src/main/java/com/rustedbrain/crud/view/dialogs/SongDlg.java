package com.rustedbrain.crud.view.dialogs;

import com.rustedbrain.networks.model.music.Song;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;

public class SongDlg extends JDialog {
    public Song song;
    public JTextField textFieldId;
    public JLabel labelId;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldName;
    private JTextField textFieldSize;
    private JTextField textFieldCreation;
    private JTextField textFieldPath;

    public SongDlg() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> {
            try {
                onOK();
            } catch (Exception e1) {
                this.song = null;
                e1.printStackTrace();
                JOptionPane.showMessageDialog(SongDlg.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    public static void main(String[] args) {
        SongDlg dialog = new SongDlg();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void onOK() throws Exception {
        this.song = new Song();
        this.song.setName(textFieldName.getText());
        this.song.setSize(Double.parseDouble(textFieldSize.getText()));
        this.song.setCreation(Date.valueOf(textFieldCreation.getText()));
        this.song.setPath(textFieldPath.getText());
        dispose();
    }

    private void onCancel() {
        this.song = null;
        dispose();
    }

    public void fillTextFields(Song song) {
        this.textFieldId.setText(song.getId().toString());
        this.textFieldName.setText(song.getName());
        this.textFieldCreation.setText(song.getCreation().toString());
        this.textFieldPath.setText(song.getPath());
        this.textFieldSize.setText(song.getSize().toString());
    }
}
