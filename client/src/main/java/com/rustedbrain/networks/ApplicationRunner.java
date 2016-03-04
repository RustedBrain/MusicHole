package com.rustedbrain.networks;

import com.rustedbrain.networks.view.MusicHoleMainWindow;
import com.rustedbrain.networks.view.logging.MusicHoleLogging;

import java.awt.*;

/**
 * Created by RustedBrain on 18.01.2016.
 */
public class ApplicationRunner {

    private static MusicHoleLogging logging;
    private static MusicHoleMainWindow mainWindow;

    public static void main(String[] args) throws InterruptedException {
        logging = new MusicHoleLogging();
        logging.pack();
        centerWindow(logging);
        logging.setVisible(true);


        if (logging.account != null) {
            mainWindow = new MusicHoleMainWindow(logging.account);
            mainWindow.pack();
            centerWindow(mainWindow);
            mainWindow.setVisible(true);
        }
    }

    public static void centerWindow(final Window window) {
        GraphicsDevice screen = MouseInfo.getPointerInfo().getDevice();
        Rectangle r = screen.getDefaultConfiguration().getBounds();
        int x = (r.width - window.getWidth()) / 2 + r.x;
        int y = (r.height - window.getHeight()) / 2 + r.y;
        window.setLocation(x, y);
    }

}
