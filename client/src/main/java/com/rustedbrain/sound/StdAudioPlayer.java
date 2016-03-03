package com.rustedbrain.sound;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

import static javax.sound.sampled.AudioSystem.*;

/**
 * Default audio player that delegates to the appropriate stream player
 */
public final class StdAudioPlayer implements AudioPlayer {

    private final AudioStreamPlayer directPlayer;
    private final AudioStreamPlayer convertingPlayer;

    public StdAudioPlayer(AudioStreamPlayer directPlayer, AudioStreamPlayer convertingPlayer) {
        this.directPlayer = directPlayer;
        this.convertingPlayer = convertingPlayer;
    }

    @Override
    public void play(final URL url) throws UnsupportedAudioFileException, IOException {
        final AudioFileFormat format = getAudioFileFormat(url);

        final AudioStreamPlayer player = isFileTypeSupported(format.getType())
                ? directPlayer : convertingPlayer;

        play(player, url);
    }

    private void play(AudioStreamPlayer player, URL url) {
        try (AudioInputStream stream = getAudioInputStream(url)) {

            player.play(stream);

        } catch (UnsupportedAudioFileException | LineUnavailableException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopAll() {
        directPlayer.stopAll();
        convertingPlayer.stopAll();
    }
}
