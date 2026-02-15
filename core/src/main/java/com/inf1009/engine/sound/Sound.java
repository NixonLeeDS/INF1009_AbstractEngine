package com.inf1009.engine.sound;

import com.badlogic.gdx.Gdx;

public class Sound extends SoundOutputDevice {

    // Field
    private String soundFile;
    private com.badlogic.gdx.audio.Sound sfx;
    private com.badlogic.gdx.audio.Music music;

    // Constructors
    public Sound() {}

    public Sound(String soundFile, boolean isMusic, int volume) {
        this.soundFile = soundFile;
        this.isMusic = isMusic;
        setVolume(volume);
    }

    // Getter / Setter
    public String getSoundFile() { return soundFile; }

    public void setSoundFile(String soundFile) {
        this.soundFile = soundFile;
    }

    // Play sound
    @Override
    public void playSound(String soundFile, boolean isMusic) {

        this.soundFile = soundFile;
        this.isMusic = isMusic;

        stopSound();

        if (soundFile == null) return;

        if (isMusic) {
            music = Gdx.audio.newMusic(Gdx.files.internal(soundFile));
            music.setVolume(volume / 100f);
            music.setLooping(true);
            music.play();
        } else {
            sfx = Gdx.audio.newSound(Gdx.files.internal(soundFile));
            sfx.play(volume / 100f);
        }
    }

    // Stop sound
    @Override
    public void stopSound() {

        if (music != null) {
            music.stop();
            music.dispose();
            music = null;
        }

        if (sfx != null) {
            sfx.stop();
            sfx.dispose();
            sfx = null;
        }
    }

    
}
