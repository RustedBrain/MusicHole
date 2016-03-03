package com.rustedbrain.sound.player;

import java.util.function.Supplier;

/**
 * Convenient creation of AudioPlayer instances
 */
public class AudioPlayerFactory implements Supplier<AudioPlayer> {

    @Override
    public AudioPlayer get() {
        return new StdAudioPlayer(
                new DirectStreamPlayer(),
                new ConvertingStreamPlayer());
    }
}
