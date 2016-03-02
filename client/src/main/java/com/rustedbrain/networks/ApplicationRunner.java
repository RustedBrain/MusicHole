package com.rustedbrain.networks;

import com.rustedbrain.networks.view.MusicHoleMainWindow;
import com.rustedbrain.networks.view.logging.MusicHoleLogging;

/**
 * Created by RustedBrain on 18.01.2016.
 */
public class ApplicationRunner {

    private static MusicHoleLogging logging;
    private static MusicHoleMainWindow mainWindow;

    public static void main(String[] args) throws InterruptedException {
        logging = new MusicHoleLogging();
        logging.pack();
        logging.setVisible(true);


        if (logging.account != null) {
            mainWindow = new MusicHoleMainWindow(logging.account);
            System.out.println(111);
            mainWindow.pack();
            mainWindow.setVisible(true);
        }
    }

}
