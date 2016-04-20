package com.rustedbrain.networks.controllers;

import javax.sound.sampled.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Paths;

/**
 * Created by RustedBrain on 18.04.2016.
 */
public class AudioController extends Thread {

    boolean stopAudioCapture = false;
    ByteArrayOutputStream byteOutputStream;
    AudioFormat adFormat;
    TargetDataLine targetDataLine;
    SourceDataLine sourceLine;
    InputStream inputStream;

    public static void main(String args[]) throws IOException, UnsupportedAudioFileException {
        AudioController audioController = new AudioController();
        audioController.captureAudio();
    }

    public static byte[] getAudioDataBytes(byte[] sourceBytes, AudioFormat audioFormat) throws UnsupportedAudioFileException, IllegalArgumentException, Exception {
        if (sourceBytes == null || sourceBytes.length == 0 || audioFormat == null) {
            throw new IllegalArgumentException("Illegal Argument passed to this method");
        }

        try (final ByteArrayInputStream bais = new ByteArrayInputStream(sourceBytes);
             final AudioInputStream sourceAIS = AudioSystem.getAudioInputStream(bais)) {
            AudioFormat sourceFormat = sourceAIS.getFormat();
            AudioFormat convertFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sourceFormat.getSampleRate(), 16, sourceFormat.getChannels(), sourceFormat.getChannels() * 2, sourceFormat.getSampleRate(), false);
            try (final AudioInputStream convert1AIS = AudioSystem.getAudioInputStream(convertFormat, sourceAIS);
                 final AudioInputStream convert2AIS = AudioSystem.getAudioInputStream(audioFormat, convert1AIS);
                 final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[8192];
                while (true) {
                    int readCount = convert2AIS.read(buffer, 0, buffer.length);
                    if (readCount == -1) {
                        break;
                    }
                    baos.write(buffer, 0, readCount);
                }
                return baos.toByteArray();
            }
        }
    }

    public void startCapture() {
        captureAudio();
    }

    public void stopCapture() {
        stopAudioCapture = true;
        targetDataLine.close();
    }

    private void captureAudio() {
        try {

            File file = Paths.get(".\\test.mp3").toFile();


            AudioInputStream in = AudioSystem.getAudioInputStream(file);

            this.inputStream = AudioSystem.getAudioInputStream(in.getFormat(), in);
            Thread captureThread = new Thread(new CaptureThread());
            captureThread.start();
        } catch (Exception e) {
            StackTraceElement stackEle[] = e.getStackTrace();
            for (StackTraceElement val : stackEle) {
                System.out.println(val);
            }
            System.exit(0);
        }
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 16000.0F;
        int sampleInbits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleInbits, channels, signed, bigEndian);
    }

    class CaptureThread extends Thread {

        byte tempBuffer[] = new byte[10000];

        public void run() {

            byteOutputStream = new ByteArrayOutputStream();
            stopAudioCapture = false;
            try {
                DatagramSocket clientSocket = new DatagramSocket(8786);
                InetAddress IPAddress = InetAddress.getByName("127.0.0.1");
                while (!stopAudioCapture) {
                    sleep(1000);
                    int cnt = inputStream.read(tempBuffer, 0, tempBuffer.length);
                    if (cnt > 0) {
                        DatagramPacket sendPacket = new DatagramPacket(tempBuffer, tempBuffer.length, IPAddress, 9786);
                        clientSocket.send(sendPacket);
                        byteOutputStream.write(tempBuffer, 0, cnt);
                    }
                }
                byteOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}
