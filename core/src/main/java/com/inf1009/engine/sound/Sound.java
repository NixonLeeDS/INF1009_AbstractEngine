package com.inf1009.engine.sound;

import com.badlogic.gdx.Gdx;

public class Sound extends SoundOutputDevice {

    private String soundFile;
    private com.badlogic.gdx.audio.Sound sfx;
    private com.badlogic.gdx.audio.Music music;

    public Sound() {}

    public Sound(String soundFile, boolean isMusic, int volume) {
        this.soundFile = soundFile;
        this.isMusic = isMusic;
        setVolume(volume);
    }

    public String getSoundFile() { return soundFile; }
    public void setSoundFile(String soundFile) { this.soundFile = soundFile; }

    // IMPORTANT: apply volume to currently playing music too
    @Override
    public void setVolume(int volume) {
        super.setVolume(volume);
        if (music != null) {
            music.setVolume(this.volume / 100f);
        }
    }

    @Override
    public void playSound(String soundFile, boolean isMusic) {

        this.soundFile = soundFile;
        this.isMusic = isMusic;

        if (soundFile == null) return;

        if (isMusic) {
            // If already have music loaded, don't recreate every time unless file changed
            if (music == null) {
                music = Gdx.audio.newMusic(Gdx.files.internal(soundFile));
                music.setLooping(true);
            }
            music.setVolume(volume / 100f);
            music.play();
        } else {
            // SFX can be recreated each time (simple)
            if (sfx != null) {
                sfx.stop();
                sfx.dispose();
                sfx = null;
            }
            sfx = Gdx.audio.newSound(Gdx.files.internal(soundFile));
            sfx.play(volume / 100f);
        }
    }

    public void pauseMusic() {
        if (music != null) music.pause();
    }

    public void resumeMusic() {
        if (music != null) music.play();
    }

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
