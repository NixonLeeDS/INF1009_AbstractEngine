package com.inf1009.engine.output;

// Minimal audio output implementation (stub for Part 1)
public class SoundOutputDevice implements Output {

    // Mute flag
    private boolean muted = false;

    @Override
    public void playSoundSoundFile(String file) {
        if (muted) return;
        // Stub: implement later if needed
    }

    @Override
    public void stopSound() {
        // Stub
    }

    @Override
    public void playMusic(String file) {
        if (muted) return;
        // Stub
    }

    @Override
    public void stopMusic() {
        // Stub
    }

    @Override
    public void mute() {
        muted = true;
    }

    @Override
    public void unmute() {
        muted = false;
    }
}
