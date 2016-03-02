package com.rustedbrain;

import com.rustedbrain.networks.model.members.Account;
import com.rustedbrain.networks.view.MusicHoleMainWindow;
import com.rustedbrain.networks.view.logging.MusicHoleLogging;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * Created by RustedBrain on 18.01.2016.
 */
public class ApplicationRunner {

    private static MusicHoleLogging logging;
    private static MusicHoleMainWindow mainWindow;

    public static void main(String[] args) throws InterruptedException {
        TransferQueue<Account> accounts = new LinkedTransferQueue<>();

        logging = new MusicHoleLogging(accounts);
        mainWindow = new MusicHoleMainWindow(accounts.take());
    }

}
