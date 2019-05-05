package com.example.river_youtube.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class YoutubeVideo implements Serializable {

    public static final String YOUTUBE_URL_PREFIX = "http://youtube.com/watch?v=";

    @SerializedName("youtubeId")
    private String youtubeId;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("title")
    private String title;

    public YoutubeVideo(String youtubeId, String thumbnail, String title) {
        this.youtubeId = youtubeId;
        this.thumbnail = thumbnail;
        this.title = title;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
