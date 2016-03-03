package com.rustedbrain.sound.player;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * For manual testing of audio file playback
 */
public class StdAudioPlayerTester {

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
        final AudioPlayer player = new AudioPlayerFactory().get();

        final String mp3 = "D:\\Media\\Music\\Ego Likeness - New Legion EP (2015)\\07. New Legion (Third Stage Remix by Stoneburner).mp3";

        playPath(player, mp3);
    }

    private static void playPath(AudioPlayer player, String path) throws IOException, UnsupportedAudioFileException {
        final URL url = Paths.get(path).toUri().toURL();
        player.play(url);
    }
}
