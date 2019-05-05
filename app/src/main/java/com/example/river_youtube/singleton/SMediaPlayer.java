package com.example.river_youtube.singleton;

import android.media.MediaPlayer;

public enum SMediaPlayer {
    INSTANCE;

    private boolean isPlaying = false;

    MediaPlayer mediaPlayer = null;
    SMediaPlayer(){
        mediaPlayer = new MediaPlayer();
    }
    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}