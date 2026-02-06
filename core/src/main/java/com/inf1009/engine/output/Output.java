package com.inf1009.engine.output;

/**
 * Output contract (audio).
 * Engine-level abstraction.
 */
public interface Output {
    void playSoundSoundFile(String file);
    void stopSound();
    void playMusic(String file);
    void stopMusic();
    void mute();
    void unmute();
}
