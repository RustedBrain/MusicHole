package com.rustedbrain.networks.controllers;

import com.rustedbrain.networks.model.messages.AudioMessage;
import com.rustedbrain.networks.model.messages.SystemMessage;
import com.rustedbrain.networks.model.music.Genre;
import com.rustedbrain.networks.model.music.Song;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by RustedBrain on 18.04.2016.
 */
public class AudioController implements Controller {

    private final Map<InetAddress, AppController.Connection> connections;
    private final SessionFactory factory;

    public AudioController(Map<InetAddress, AppController.Connection> connections, SessionFactory factory) {
        this.connections = connections;
        this.factory = factory;
    }

    @Override
    public void handleMessage(SystemMessage message, Socket clientSocket) {
        AudioMessage audioMessage = (AudioMessage) message;
        System.out.println("Audio controller received new message: " + message);
        new AudioHandler(audioMessage, clientSocket).start();
    }


    private class AudioHandler extends Thread {

        private final AudioMessage message;
        private final Socket client;

        public AudioHandler(AudioMessage message, Socket clientSocket) {
            this.message = message;
            this.client = clientSocket;
        }

        @Override
        public void run() {

            Object object = message.getObject();

            if (object == null) {
                Session session = factory.openSession();
                Criteria criteria = session.createCriteria(Genre.class);
                ArrayList list = new ArrayList<>(criteria.list());
                session.close();
                try {
                    new ObjectOutputStream(client.getOutputStream()).writeObject(list);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (object instanceof Song) {
                Song song = (Song) object;
                System.out.println("Audio message object: song");
            }

//            File file = new File("filename.mp3");
//            AudioFileFormat baseFileFormat = null;
//            try {
//                baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(file);
//            } catch (UnsupportedAudioFileException | IOException e) {
//                e.printStackTrace();
//            }
//            Map properties = baseFileFormat.properties();
//            Long duration = (Long) properties.get("duration");
//            System.out.println(duration);
        }
    }
}
