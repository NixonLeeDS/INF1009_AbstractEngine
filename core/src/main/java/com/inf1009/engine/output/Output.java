package com.inf1009.engine.output;

// Generic output contract (audio abstraction)
public interface Output {

    // Play a short sound effect
    void playSoundSoundFile(String file);

    // Stop sound playback
    void stopSound();

    // Play background music
    void playMusic(String file);

    // Stop background music
    void stopMusic();

    // Mute all audio
    void mute();

    // Unmute all audio
    void unmute();
}
