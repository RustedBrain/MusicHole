package com.rustedbrain.sound.player;

import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;
import java.io.IOException;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;

/**
 * Converts and plays audio stream (based on conversion
 * support found in the Java audio system)
 */
public final class ConvertingStreamPlayer implements AudioStreamPlayer {

    private static final int DEFAULT_BUFFER_SIZE = 65536;

    private final Deque<SourceDataLine> linesPlaying = new ConcurrentLinkedDeque<>();

    private final int bufferSize;

    public ConvertingStreamPlayer() {
        this.bufferSize = DEFAULT_BUFFER_SIZE;
    }

    public ConvertingStreamPlayer(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    private static AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();
        final float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }

    @Override
    public void play(final AudioInputStream stream)
            throws LineUnavailableException, IOException {

        final AudioFormat outFormat = getOutFormat(stream.getFormat());
        final Info info = new Info(SourceDataLine.class, outFormat);

        try (final SourceDataLine line =
                     (SourceDataLine) AudioSystem.getLine(info)) {

            if (line != null) {
                line.open(outFormat);
                linesPlaying.add(line);
                line.start();
                stream(getAudioInputStream(outFormat, stream), line);
                line.drain();
                line.stop();
                linesPlaying.remove(line);
            }
        }
    }

    @Override
    public void stopAll() {
        while (linesPlaying.peek() != null) {
            try (final SourceDataLine line = linesPlaying.pop()) {
                line.stop();
            }
        }
    }

    private void stream(AudioInputStream in, SourceDataLine line) throws IOException {
        final byte[] buffer = new byte[bufferSize];
        for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
            line.write(buffer, 0, n);
        }
    }
}