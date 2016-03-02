package com.rustedbrain.crud;

import com.rustedbrain.crud.utils.EntityController;
import com.rustedbrain.crud.utils.EntityUtil;
import com.rustedbrain.crud.view.MainWindow;
import com.rustedbrain.networks.model.members.Account;
import com.rustedbrain.networks.model.music.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by RustedBrain on 25.02.2016.
 */
public class CRUDApp {

    private static SessionFactory factory;

    public static void main(String[] args) {
        factory = new Configuration().configure().buildSessionFactory();

        EntityUtil<Account> accountUtil = new EntityUtil<>(factory.openSession(), Account.class);
        EntityUtil<Genre> genreUtil = new EntityUtil<>(factory.openSession(), Genre.class);
        EntityUtil<Album> albumUtil = new EntityUtil<>(factory.openSession(), Album.class);
        EntityUtil<Group> groupUtil = new EntityUtil<>(factory.openSession(), Group.class);
        EntityUtil<Member> memberUtil = new EntityUtil<>(factory.openSession(), Member.class);
        EntityUtil<Song> songUtil = new EntityUtil<>(factory.openSession(), Song.class);

        EntityController entityController = new EntityController(factory.openSession());
        entityController.setAccountUtil(accountUtil);
        entityController.setAlbumUtil(albumUtil);
        entityController.setGenreUtil(genreUtil);
        entityController.setGroupUtil(groupUtil);
        entityController.setSongUtil(songUtil);
        entityController.setMemberUtil(memberUtil);

        MainWindow mainWindow = new MainWindow();
        mainWindow.setController(entityController);
        mainWindow.pack();
        mainWindow.setVisible(true);

        System.out.println("Exit");
        factory.close();
    }

}
