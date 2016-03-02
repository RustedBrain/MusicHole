package com.rustedbrain.crud.view;

import com.rustedbrain.crud.utils.EntityController;
import com.rustedbrain.crud.view.dialogs.*;
import com.rustedbrain.networks.model.members.Account;
import com.rustedbrain.networks.model.music.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

public class MainWindow extends JDialog {
    private DefaultTableModel tableModelAccounts;
    private DefaultTableModel tableModelGenres;
    private DefaultTableModel tableModelGroups;
    private DefaultTableModel tableModelMembers;
    private DefaultTableModel tableModelAlbums;
    private DefaultTableModel tableModelSongs;
    private JPanel contentPane;
    private JTabbedPane tabbedPaneMain;
    private JButton createButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JComboBox comboBoxCurrentTable;
    private JButton refreshButton;
    private JTextArea textArea1;
    private JButton getSummaryMusicSizeButton;
    private JTable tableTableView2;
    private JButton refreshViewButton;
    private EntityController controller;
    private AccountDlg accountDlg;
    private GenreDlg genreDlg;
    private GroupDlg groupDlg;
    private MemberDlg memberDlg;
    private AlbumDlg albumDlg;
    private SongDlg songDlg;

    {
        this.accountDlg = new AccountDlg();
        this.genreDlg = new GenreDlg();
        this.groupDlg = new GroupDlg();
        this.memberDlg = new MemberDlg();
        this.albumDlg = new AlbumDlg();
        this.songDlg = new SongDlg();

        this.comboBoxCurrentTable.setModel(new DefaultComboBoxModel(TableEntity.values()));
    }

    public MainWindow() {
        setContentPane(contentPane);
        setModal(true);
        createButton.addActionListener(e -> {
            try {
                onCreateButtonCLicked();
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(MainWindow.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        refreshButton.addActionListener(e -> onRefreshButtonClicked());
        deleteButton.addActionListener(e -> onDeleteButtonClicked());
        comboBoxCurrentTable.addActionListener(e -> onComboBoxValueChanged());
        updateButton.addActionListener(e -> {
            try {
                onUpdateClicked();
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(MainWindow.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        getSummaryMusicSizeButton.addActionListener(e -> onSelectSummaryMusicSizeClicked());
    }

    private void onSelectSummaryMusicSizeClicked() {
        this.textArea1.setText("Summary playlist time: " + controller.getSongSizeSum());
    }


    private void onUpdateClicked() throws Exception {
        switch (getCurrentTable()) {
            case ACCOUNT: {
                Point idValue = findSelectedValue("id");
                Account oldAccount = this.controller.getAccount(Integer.parseInt((String) tableTableView2.getValueAt((int) idValue.getY(), (int) idValue.getX())));
                this.accountDlg.labelId.setVisible(true);
                this.accountDlg.textFieldId.setVisible(true);
                this.accountDlg.fillTextFields(oldAccount);
                this.accountDlg.pack();
                this.accountDlg.setVisible(true);
                if (this.accountDlg.account != null) {
                    try {
                        this.controller.updateAccount(oldAccount, this.accountDlg.account);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            break;
            case GENRE: {
                this.genreDlg.labelId.setVisible(true);
                this.genreDlg.textFieldId.setVisible(true);
                this.genreDlg.pack();
                this.genreDlg.setVisible(true);
                if (this.genreDlg.genre != null) {
                    try {
                        Point idValue = findSelectedValue("id");
                        this.controller.updateGenre(this.controller.getGenre(Integer.parseInt((String) tableTableView2.getValueAt((int) idValue.getY(), (int) idValue.getX()))), this.genreDlg.genre);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            break;
            case GROUP: {
                this.groupDlg.labelId.setVisible(true);
                this.groupDlg.textFieldId.setVisible(true);
                this.groupDlg.pack();
                this.groupDlg.setVisible(true);
                if (this.groupDlg.group != null) {
                    try {
                        Point idValue = findSelectedValue("id");
                        this.controller.updateGroup(this.controller.getGroup(Integer.parseInt((String) tableTableView2.getValueAt((int) idValue.getY(), (int) idValue.getX()))), this.groupDlg.group);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            break;
            case MEMBER: {
                this.memberDlg.labelId.setVisible(true);
                this.memberDlg.textFieldId.setVisible(true);
                this.memberDlg.pack();
                this.memberDlg.setVisible(true);
                if (this.memberDlg.member != null) {
                    try {
                        Point idValue = findSelectedValue("id");
                        this.controller.updateMember(this.controller.getMember(Integer.parseInt((String) tableTableView2.getValueAt((int) idValue.getY(), (int) idValue.getX()))), this.memberDlg.member);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            break;
            case ALBUM: {
                this.albumDlg.labelId.setVisible(true);
                this.albumDlg.textFieldId.setVisible(true);
                this.albumDlg.pack();
                this.albumDlg.setVisible(true);
                if (this.albumDlg.album != null) {
                    try {
                        Point idValue = findSelectedValue("id");
                        this.controller.updateAlbum(this.controller.getAlbum(Integer.parseInt((String) tableTableView2.getValueAt((int) idValue.getY(), (int) idValue.getX()))), this.albumDlg.album);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            break;
            case SONG: {
                this.songDlg.labelId.setVisible(true);
                this.songDlg.textFieldId.setVisible(true);
                this.songDlg.pack();
                this.songDlg.setVisible(true);
                if (this.songDlg.song != null) {
                    try {
                        Point idValue = findSelectedValue("id");
                        this.controller.updateSong(this.controller.getSong(Integer.parseInt((String) tableTableView2.getValueAt((int) idValue.getY(), (int) idValue.getX()))), this.songDlg.song);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            break;
        }
    }

    private void onComboBoxValueChanged() {
        switch (getCurrentTable()) {
            case ACCOUNT: {
                if (tableModelAccounts != null) {
                    this.tableTableView2.setModel(tableModelAccounts);
                    this.tableModelAccounts.fireTableDataChanged();
                }
            }
            break;
            case GENRE: {
                if (tableModelGenres != null) {
                    this.tableTableView2.setModel(tableModelGenres);
                    tableModelGenres.fireTableDataChanged();
                }
            }
            break;
            case GROUP: {
                if (tableModelGroups != null) {
                    this.tableTableView2.setModel(tableModelGroups);
                    tableModelGroups.fireTableDataChanged();
                }
            }
            break;
            case MEMBER: {
                if (tableModelMembers != null) {
                    this.tableTableView2.setModel(tableModelMembers);
                    this.tableModelMembers.fireTableDataChanged();
                }
            }
            break;
            case ALBUM: {
                if (tableModelAlbums != null) {
                    this.tableTableView2.setModel(tableModelAlbums);
                    this.tableModelAlbums.fireTableDataChanged();
                }
            }
            break;
            case SONG: {
                if (tableModelSongs != null) {
                    this.tableTableView2.setModel(tableModelSongs);
                    tableModelSongs.fireTableDataChanged();
                }
            }
            break;
        }
    }

    private void onDeleteButtonClicked() {
        switch (getCurrentTable()) {
            case ACCOUNT: {
                try {
                    Point idValue = findSelectedValue("id");
                    controller.deleteAccount(Integer.parseInt((String) tableTableView2.getValueAt((int) idValue.getY(), (int) idValue.getX())));
                    ((DefaultTableModel) tableTableView2.getModel()).removeRow((int) idValue.getY());
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            break;
            case GENRE: {
                try {
                    Point idValue = findSelectedValue("id");
                    controller.deleteGenre(Integer.parseInt((String) tableTableView2.getValueAt((int) idValue.getY(), (int) idValue.getX())));
                    ((DefaultTableModel) tableTableView2.getModel()).removeRow((int) idValue.getY());
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            break;
            case GROUP: {
                try {
                    Point idValue = findSelectedValue("id");
                    controller.deleteGroup(Integer.parseInt((String) tableTableView2.getValueAt((int) idValue.getY(), (int) idValue.getX())));
                    ((DefaultTableModel) tableTableView2.getModel()).removeRow((int) idValue.getY());
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            break;
            case MEMBER: {
                try {
                    Point idValue = findSelectedValue("id");
                    controller.deleteMember(Integer.parseInt((String) tableTableView2.getValueAt((int) idValue.getY(), (int) idValue.getX())));
                    ((DefaultTableModel) tableTableView2.getModel()).removeRow((int) idValue.getY());
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            break;
            case ALBUM: {
                try {
                    Point idValue = findSelectedValue("id");
                    controller.deleteAlbum(Integer.parseInt((String) tableTableView2.getValueAt((int) idValue.getY(), (int) idValue.getX())));
                    ((DefaultTableModel) tableTableView2.getModel()).removeRow((int) idValue.getY());
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            break;
            case SONG: {
                try {
                    Point idValue = findSelectedValue("id");
                    controller.deleteSong(Integer.parseInt((String) tableTableView2.getValueAt((int) idValue.getY(), (int) idValue.getX())));
                    ((DefaultTableModel) tableTableView2.getModel()).removeRow((int) idValue.getY());
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            break;
        }
    }

    private void onRefreshButtonClicked() {
        switch (getCurrentTable()) {
            case ACCOUNT: {
                this.tableModelAccounts = fillTableModel(controller.getAccounts(), Account.class);
                this.tableTableView2.setModel(this.tableModelAccounts);
            }
            break;
            case GENRE: {
                this.tableModelGenres = fillTableModel(controller.getGenres(), Genre.class);
                this.tableTableView2.setModel(this.tableModelGenres);
            }
            break;
            case GROUP: {
                this.tableModelGroups = fillTableModel(controller.getGroups(), Group.class);
                this.tableTableView2.setModel(this.tableModelGroups);
            }
            break;
            case MEMBER: {
                this.tableModelMembers = fillTableModel(controller.getMembers(), Member.class);
                this.tableTableView2.setModel(this.tableModelMembers);
            }
            break;
            case ALBUM: {
                this.tableModelAlbums = fillTableModel(controller.getAlbums(), Album.class);
                this.tableTableView2.setModel(this.tableModelAlbums);
            }
            break;
            case SONG: {
                this.tableModelSongs = fillTableModel(controller.getSongs(), Song.class);
                this.tableTableView2.setModel(this.tableModelSongs);
            }
            break;
        }
    }

    private ArrayList<Field> getAllFields(Class c, ArrayList<Field> fields) {
        if (c.getSuperclass() != null)
            fields = getAllFields(c.getSuperclass(), fields);
        Collections.addAll(fields, c.getDeclaredFields());
        return fields;
    }

    private DefaultTableModel fillTableModel(java.util.List entities, Class c) {
        ArrayList<Field> fields = getAllFields(c, new ArrayList<>());

        Object[] columnNames = new Object[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            columnNames[i] = fields.get(i).getName();
        }

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, entities.size());

        for (int i = 0; i < entities.size(); i++) {

            for (int j = 0; j < fields.size(); j++) {
                try {
                    Field field = fields.get(j);
                    field.setAccessible(true);
                    String value = "";
                    if (field.get(entities.get(i)) != null) {
                        value = field.get(entities.get(i)).toString();
                    }
                    tableModel.setValueAt(value, i, j);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return tableModel;
    }

    private void onCreateButtonCLicked() throws Exception {
        switch (getCurrentTable()) {
            case ACCOUNT: {
                this.accountDlg.labelId.setVisible(false);
                this.accountDlg.textFieldId.setVisible(false);
                this.accountDlg.pack();
                this.accountDlg.setVisible(true);
                if (this.accountDlg.account != null) {
                    try {
                        this.controller.createAccount(this.accountDlg.account);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            break;
            case GENRE: {
                this.genreDlg.labelId.setVisible(false);
                this.genreDlg.textFieldId.setVisible(false);
                this.genreDlg.pack();
                this.genreDlg.setVisible(true);
                if (this.genreDlg.genre != null) {
                    try {
                        this.controller.createGenre(this.genreDlg.genre);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            break;
            case GROUP: {
                this.groupDlg.labelId.setVisible(false);
                this.groupDlg.textFieldId.setVisible(false);
                this.groupDlg.pack();
                this.groupDlg.setVisible(true);
                if (this.groupDlg.group != null) {
                    try {
                        this.controller.createGroup(this.groupDlg.group);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            break;
            case MEMBER: {
                this.memberDlg.labelId.setVisible(false);
                this.memberDlg.textFieldId.setVisible(false);
                this.memberDlg.pack();
                this.memberDlg.setVisible(true);
                if (this.memberDlg.member != null) {
                    try {
                        this.controller.createMember(this.memberDlg.member);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            break;
            case ALBUM: {
                this.albumDlg.labelId.setVisible(false);
                this.albumDlg.textFieldId.setVisible(false);
                this.albumDlg.pack();
                this.albumDlg.setVisible(true);
                if (this.albumDlg.album != null) {
                    try {
                        this.controller.createAlbum(this.albumDlg.album);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            break;
            case SONG: {
                this.songDlg.labelId.setVisible(false);
                this.songDlg.textFieldId.setVisible(false);
                this.songDlg.pack();
                this.songDlg.setVisible(true);
                if (this.songDlg.song != null) {
                    try {
                        this.controller.createSong(this.songDlg.song);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            break;
        }
    }

    public void setController(EntityController controller) {
        this.controller = controller;
    }

    private Point findSelectedValue(String columnName) throws Exception {
        for (int i = 0; i < tableTableView2.getColumnCount(); i++) {
            if (tableTableView2.getColumnName(i).equalsIgnoreCase(columnName))
                return new Point(i, tableTableView2.getSelectedRow());
        }
        throw new Exception();
    }

    private TableEntity getCurrentTable() {
        return (TableEntity) this.comboBoxCurrentTable.getSelectedItem();
    }
}
