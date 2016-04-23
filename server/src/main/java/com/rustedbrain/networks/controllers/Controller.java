package com.rustedbrain.networks.controllers;

import com.rustedbrain.networks.model.messages.SystemMessage;

import java.net.Socket;

/**
 * Created by RustedBrain on 22.04.2016.
 */
public interface Controller {

    void handleMessage(SystemMessage message, Socket clientSocket);

}
