package com.example.river_youtube.actions;

import android.media.MediaPlayer;

import com.example.river_youtube.singleton.SMediaPlayer;

public class VideoActions {
    private static MediaPlayer mediaPlayer = SMediaPlayer.INSTANCE.getMediaPlayer();

    public static void playNPause() {
        if (SMediaPlayer.INSTANCE.isPlaying()) {
            mediaPlayer.pause();
            SMediaPlayer.INSTANCE.setPlaying(false);
        } else {
            mediaPlayer.start();
            SMediaPlayer.INSTANCE.setPlaying(true);

        }
    }
}
