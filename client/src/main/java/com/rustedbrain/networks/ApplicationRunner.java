package com.rustedbrain.networks;

import com.rustedbrain.networks.view.MusicHoleMainWindow;
import com.rustedbrain.networks.view.ViewUtil;
import com.rustedbrain.networks.view.logging.MusicHoleLogging;

/**
 * Created by RustedBrain on 18.01.2016.
 */
public class ApplicationRunner {

    private static final int VERTICAL_SIZE_MAGIC_CONSTANT = 600;
    private static final int HORIZONTAL_SIZE_MAGIC_CONSTANT = 800;
    private static MusicHoleLogging logging;
    private static MusicHoleMainWindow mainWindow;

    public static void main(String[] args) throws InterruptedException {
        //Logging chapter
        logging = new MusicHoleLogging();
        logging.pack();
        ViewUtil.centerWindow(logging);
        logging.setVisible(true);

        if (logging.account != null) {

            //Main window chapter
            mainWindow = new MusicHoleMainWindow(logging.account);
            mainWindow.setSize(HORIZONTAL_SIZE_MAGIC_CONSTANT, VERTICAL_SIZE_MAGIC_CONSTANT);
            ViewUtil.centerWindow(mainWindow);
            mainWindow.setVisible(true);
        }
    }

}
