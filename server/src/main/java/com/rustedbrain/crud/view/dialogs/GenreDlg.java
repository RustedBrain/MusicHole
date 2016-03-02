package com.rustedbrain.crud.view.dialogs;

import com.rustedbrain.networks.model.music.Genre;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GenreDlg extends JDialog {
    public JTextField textFieldId;
    public JLabel labelId;
    public Genre genre;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldName;
    private JTextField textFieldDescription;

    public GenreDlg() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> {
            try {
                onOK();
            } catch (Exception e1) {
                this.genre = null;
                e1.printStackTrace();
                JOptionPane.showMessageDialog(GenreDlg.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        this.genre = new Genre();
        this.genre.setName(textFieldName.getText());
        this.genre.setDescription(textFieldDescription.getText());
        dispose();
    }

    private void onCancel() {
        this.genre = null;
        dispose();
    }

    public void fillTextFields(Genre genre) {
        this.textFieldId.setText(genre.getId().toString());
        this.textFieldName.setText(genre.getName());
        this.textFieldDescription.setText(genre.getDescription());
    }
}
