package com.example.river_youtube;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.river_youtube.model.YoutubeVideo;
import com.example.river_youtube.singleton.SMediaPlayer;

import java.io.IOException;
import java.util.HashMap;

import at.huber.youtubeExtractor.Format;
import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class BackgroundAudioPlayingIntentService extends IntentService {

    private YouTubeExtractor youTubeExtractor;
    private static MediaPlayer mediaPlayer = null;

    private static final String ACTION_PREPARE_VIDEO = "com.example.river_youtube.action.ACTION_PREPARE_VIDEO";

    private static Context mContext = null;


    public BackgroundAudioPlayingIntentService() {
        super("BackgroundAudioPlayingIntentService");
        mediaPlayer = SMediaPlayer.INSTANCE.getMediaPlayer();
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void init(Context context){
        mContext = context;
    }

    public static void startActionPrepareVideo(YoutubeVideo youtubeVideo) {
        Intent intent = new Intent(mContext, BackgroundAudioPlayingIntentService.class);
        intent.setAction(ACTION_PREPARE_VIDEO);
        Bundle b = new Bundle();
        b.putSerializable("youtubeVideo", youtubeVideo);
        intent.putExtras(b);
        mContext.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PREPARE_VIDEO.equals(action)) {
                handleActionPlayVideo(intent);
            }
        }
    }


    private void handleActionPlayVideo(Intent intent) {
        Bundle bundle = intent.getExtras();
        YoutubeVideo youtubeVideo = (YoutubeVideo)bundle.getSerializable("youtubeVideo");
        youTubeExtractor = new YouTubeExtractor(this) {

            @Override
            protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {
                YtFile ytFile = null;
                int maxAudioBitrate = -1;
                for (int i = 0; i < ytFiles.size(); i++) {
                    YtFile _ytFile = ytFiles.get(ytFiles.keyAt(i));
                    Format format = _ytFile.getFormat();
                    int height = format.getHeight();
                    if (height == -1 && format.getAudioBitrate() > maxAudioBitrate) {
                        maxAudioBitrate = format.getAudioBitrate();
                        ytFile = _ytFile;
                    }
                }
                if (ytFile == null) {
                    return;
                }
                String downloadUrl = ytFile.getUrl();
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDataSource(ytFile.getUrl());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
//                    Toast.makeText(getApplicationContext(), "prepared", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        youTubeExtractor.extract(youtubeVideo.YOUTUBE_URL_PREFIX+youtubeVideo.getYoutubeId(), true, true);
    }

    public static void play() {
        mediaPlayer.start();
    }
}
