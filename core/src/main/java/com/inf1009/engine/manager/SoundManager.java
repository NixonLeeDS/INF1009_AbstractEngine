package com.inf1009.engine.manager;

import com.inf1009.engine.entity.GameEntity;
import com.inf1009.engine.interfaces.ICollidableListener;
import com.inf1009.engine.interfaces.IVolume;
import com.inf1009.engine.sound.Sound;
import java.util.ArrayList;
import java.util.List;


public class SoundManager implements IVolume, ICollidableListener {

    // Fields
    private List<Sound> soundList = new ArrayList<>();
    private int masterVol = 100;
    private int musicVol = 100;
    private boolean muted = false;

    // Add sound to manager
    public void addSound(Sound s) {
        if (s != null) soundList.add(s);
    }

    // Mute all sounds
    public void mute() {
        muted = true;
        for (Sound s : soundList) {
            s.setVolume(0);
        }
    }

    // Restore volumes
    public void unmute() {
        muted = false;
        for (Sound s : soundList) {
            int volume = s.isMusic() ? musicVol : masterVol;
            s.setVolume(volume);
        }
    }

    // IVolume methods
    @Override
    public int getMasterVol() {
        return masterVol;
    }

    @Override
    public void setMasterVol(int vol) {
        masterVol = clamp(vol);
        if (!muted) unmute();
    }

    @Override
    public int getMusicVol() {
        return musicVol;
    }

    @Override
    public void setMusicVol(int vol) {
        musicVol = clamp(vol);
        if (!muted) unmute();
    }

    // Utility
    private int clamp(int v) {
        if (v < 0) return 0;
        if (v > 100) return 100;
        return v;
    }


    //collidable listener
    @Override
    public void onCollision(GameEntity entity1, GameEntity entity2) {
        if (!muted) {
            for (Sound s : soundList) {
                if (!s.isMusic()) {   // play only SFX, not music
                    s.playSound(s.getSoundFile(), s.isMusic());

                }
            }
        }
    }

}
