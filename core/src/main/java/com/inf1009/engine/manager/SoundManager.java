package com.inf1009.engine.manager;
import com.inf1009.engine.output.Sound;

public class SoundManager {

    private float masterVol = 1f;
    private float musicVolume = 1f;
    private float sfxVolume = 1f;
    private boolean mute = false;

    public void setMasterVol(float vol) {
        this.masterVol = vol;
    }

    public void setMusicVol(float vol) {
        this.musicVolume = vol;
    }

    public void setSfxVol(float vol) {
        this.sfxVolume = vol;
    }

    public void playSound(String name) {
        if (mute) return;

        Sound sound = new Sound(name);
        sound.playSound(name);
    }

    public void playMusic(String name, boolean loop) {
        if (mute) return;

        Sound music = new Sound(name, true, (int)(musicVolume * 100));
        music.playMusic(name);
    }

    public void stopMusic() {
        // simplified version
    }

    public void mute() {
        mute = true;
    }

    public void unmute() {
        mute = false;
    }
}
