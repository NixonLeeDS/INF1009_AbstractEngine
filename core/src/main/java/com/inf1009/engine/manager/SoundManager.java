package com.inf1009.engine.manager;

import com.inf1009.engine.entity.DynamicEntity;
import com.inf1009.engine.entity.GameEntity;
import com.inf1009.engine.interfaces.ICollidableListener;
import com.inf1009.engine.interfaces.ISoundManager;
import com.inf1009.engine.interfaces.IVolume;
import com.inf1009.engine.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class SoundManager implements IVolume, ICollidableListener, ISoundManager {

    private final List<Sound> soundList = new ArrayList<>();
    private int masterVol = 100;
    private int musicVol = 100;
    private boolean muted = false;

    // Track BGM so unmute can resume
    private String currentMusicFile = null;

    // Setup
    public void addSound(Sound s) {
        if (s != null) soundList.add(s);
    }

    // ISoundManager
    @Override
    public void playSound(String name) {
        if (muted) return;

        for (Sound s : soundList) {
            if (!s.isMusic() && name != null && name.equals(s.getSoundFile())) {
                s.setVolume(masterVol);
                s.playSound(name, false);
                return;
            }
        }

        // fallback: play any first SFX if exact match not found
        for (Sound s : soundList) {
            if (!s.isMusic()) {
                s.setVolume(masterVol);
                s.playSound(name, false);
                return;
            }
        }
    }

    @Override
    public void playMusic(String name) {
        currentMusicFile = name;
        if (muted) return;

        for (Sound s : soundList) {
            if (s.isMusic()) {
                s.setSoundFile(name);
                s.setVolume(musicVol);
                s.playSound(name, true);
                return;
            }
        }
    }

    @Override
    public void stopMusic() {
        for (Sound s : soundList) {
            if (s.isMusic()) {
                s.stopSound();
            }
        }
    }

    @Override
    public void pauseMusic() {
        for (Sound s : soundList) {
            if (s.isMusic()) {
                s.pauseMusic();
            }
        }
    }

    @Override
    public void resumeMusic() {
        if (muted) return;

        // resume existing music instance
        for (Sound s : soundList) {
            if (s.isMusic()) {
                s.setVolume(musicVol);
                s.resumeMusic();
                return;
            }
        }

        // fallback: if disposed earlier, restart
        if (currentMusicFile != null) {
            playMusic(currentMusicFile);
        }
    }

    @Override
    public int getMasterVolume() {
        return masterVol;
    }

    @Override
    public void setMasterVolume(int volume) {
        masterVol = clamp(volume);
        if (!muted) applyVolumes();
    }

    @Override
    public int getMusicVolume() {
        return musicVol;
    }

    @Override
    public void setMusicVolume(int volume) {
        musicVol = clamp(volume);
        if (!muted) applyVolumes();
    }

    @Override
    public void mute() {
        muted = true;

        // SFX volume to 0 (future SFX blocked by playSound() early return)
        for (Sound s : soundList) {
            if (!s.isMusic()) s.setVolume(0);
        }

        // Pause music + volume 0
        for (Sound s : soundList) {
            if (s.isMusic()) {
                s.setVolume(0);
                s.pauseMusic();
            }
        }
    }

    @Override
    public void unmute() {
        muted = false;
        applyVolumes();
        resumeMusic();
    }

    @Override
    public void dispose() {
        for (Sound s : soundList) {
            s.stopSound();
        }
        soundList.clear();
    }

    // IVolume (your old interface)
    @Override
    public int getMasterVol() { return masterVol; }

    @Override
    public void setMasterVol(int vol) { setMasterVolume(vol); }

    @Override
    public int getMusicVol() { return musicVol; }

    @Override
    public void setMusicVol(int vol) { setMusicVolume(vol); }

    // Collision SFX hook
    @Override
    public void onCollision(GameEntity e1, GameEntity e2) {

        if ((e1.getClass() == DynamicEntity.class &&
            e2.getClass() == DynamicEntity.class)) {

            playSound("audio/hit.wav");
        }
    }


    // Helpers
    private void applyVolumes() {
        for (Sound s : soundList) {
            if (s.isMusic()) s.setVolume(musicVol);
            else s.setVolume(masterVol);
        }
    }

    private int clamp(int v) {
        if (v < 0) return 0;
        if (v > 100) return 100;
        return v;
    }
}
