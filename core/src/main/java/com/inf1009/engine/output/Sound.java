package com.inf1009.engine.output;

public class Sound extends AbstractSoundOutputDevice {

    private String soundFile;

    public Sound() {
    }

    public Sound(String soundFile) {
        this.soundFile = soundFile;
    }

    public Sound(String soundFile, boolean isMusic, int volume) {
        this.soundFile = soundFile;
        this.isMusic = isMusic;
        this.volume = volume;
    }

    public void setSoundFile(String soundFile) {
        this.soundFile = soundFile;
    }

    public String getSoundFile() {
        return soundFile;
    }

    @Override
    public void playSound(String soundFile) {
        super.playSound(soundFile);
    }

    @Override
    public void playMusic(String musicFile) {
        super.playMusic(musicFile);
    }
}
