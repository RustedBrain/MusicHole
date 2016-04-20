package com.rustedbrain.sound.VU;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class VUServer {
    ByteArrayOutputStream byteOutputStream;
    AudioFormat adFormat;
    TargetDataLine targetDataLine;
    AudioInputStream InputStream;
    SourceDataLine sourceLine;

    public static void main(String args[]) {
        new VUServer().runVOIP();
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 16000.0F;
        int sampleInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleInBits, channels, signed, bigEndian);
    }

    public void runVOIP() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9786);
            byte[] receiveData = new byte[10000];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                System.out.println("RECEIVED: " + receivePacket.getAddress().getHostAddress() + " " + receivePacket.getPort());
                try {
                    byte audioData[] = receivePacket.getData();
                    InputStream byteInputStream = new ByteArrayInputStream(audioData);
                    AudioFormat adFormat = getAudioFormat();
                    InputStream = new AudioInputStream(byteInputStream, adFormat, audioData.length / adFormat.getFrameSize());
                    DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, adFormat);
                    sourceLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                    sourceLine.open(adFormat);
                    sourceLine.start();
                    Thread playThread = new Thread(new PlayThread());
                    playThread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class PlayThread extends Thread {

        byte tempBuffer[] = new byte[10000];

        public void run() {
            try {
                int cnt;
                while ((cnt = InputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
                    if (cnt > 0) {
                        sourceLine.write(tempBuffer, 0, cnt);
                    }
                }
                //  sourceLine.drain();
                // sourceLine.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}
