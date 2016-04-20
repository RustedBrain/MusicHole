package com.rustedbrain.networks.controllers;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by RustedBrain on 19.04.2016.
 */
public class AudioTest extends Thread {

    private String filename;

    public AudioTest(String filename) {
        super();
        this.filename = filename;
    }

    public static void main(String[] args) {
        // my relative path file name
        String song = "Bondan ft. Fade2Black-Ya Sudahlah.mp3";
        AudioTest mp3Sound = new AudioTest(song);
        mp3Sound.start();
    }

    @Override
    public void run() {
        try {
            File file = new File(filename);

            AudioInputStream in = AudioSystem.getAudioInputStream(file);
            AudioInputStream din = null;
            AudioFormat baseFormat = in.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
            din = AudioSystem.getAudioInputStream(decodedFormat, in);

            // play it...
            rawplay(decodedFormat, din);
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void rawplay(AudioFormat targetFormat, AudioInputStream din) throws IOException, LineUnavailableException {
        byte[] data = new byte[4096];
        SourceDataLine line = getLine(targetFormat);
        if (line != null) {
            // Start
            line.start();
            int nBytesRead = 0, nBytesWritten = 0;
            while (nBytesRead != -1) {
                nBytesRead = din.read(data, 0, data.length);
                if (nBytesRead != -1) {
                    nBytesWritten = line.write(data, 0, nBytesRead);
                }

            }
            // Stop
            line.drain();
            line.stop();
            line.close();
            din.close();
        }

    }

    private synchronized SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException {
        SourceDataLine res = null;
        DataLine.Info info =
                new DataLine.Info(SourceDataLine.class, audioFormat);
        res = (SourceDataLine) AudioSystem.getLine(info);
        res.open(audioFormat);

        return res;
    }
}
