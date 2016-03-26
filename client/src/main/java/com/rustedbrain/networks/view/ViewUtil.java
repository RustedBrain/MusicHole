package com.rustedbrain.networks.view;

import java.awt.*;

/**
 * Created by RustedBrain on 04.03.2016.
 */
public class ViewUtil {

    public static void centerWindow(final Window window) {
        GraphicsDevice screen = MouseInfo.getPointerInfo().getDevice();
        Rectangle r = screen.getDefaultConfiguration().getBounds();
        int x = (r.width - window.getWidth()) / 2 + r.x;
        int y = (r.height - window.getHeight()) / 2 + r.y;
        window.setLocation(x, y);
    }

}
