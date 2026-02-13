package com.inf1009.engine.output;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public abstract class AbstractSoundOutputDevice {

    protected int volume = 100;
    protected boolean isMusic = false;
    protected float sfxVolume = 1f;
    protected boolean mute = false;

    protected Music currentMusic;

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void mute() {
        mute = true;
        if (currentMusic != null) currentMusic.pause();
    }

    public void unmute() {
        mute = false;
        if (currentMusic != null) currentMusic.play();
    }

    public void playSound(String soundFile) {
        if (mute) return;

        Sound sound = Gdx.audio.newSound(Gdx.files.internal(soundFile));
        sound.play(sfxVolume);
    }

    public void stopSound() {
        // simple version, nothing tracked
    }

    public void playMusic(String musicFile) {
        if (mute) return;

        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }

        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(musicFile));
        currentMusic.setLooping(true);
        currentMusic.setVolume(volume / 100f);
        currentMusic.play();
        isMusic = true;
    }

    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
    }
}
