package com.inf1009.engine.output;

/**
 * Minimal audio output implementation.
 * Part 1 can be stubbed (no-op) to keep engine abstract and compilable.
 */
public class SoundOutputDevice implements Output {

    private boolean muted = false;

    @Override
    public void playSoundSoundFile(String file) {
        if (muted) return;
        // Part 1 stub: implement with Gdx.audio later if needed.
    }

    @Override
    public void stopSound() {
        // Part 1 stub
    }

    @Override
    public void playMusic(String file) {
        if (muted) return;
        // Part 1 stub
    }

    @Override
    public void stopMusic() {
        // Part 1 stub
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
