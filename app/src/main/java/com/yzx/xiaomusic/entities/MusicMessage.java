package com.yzx.xiaomusic.entities;

/**
 * Created by yzx on 2018/1/26.
 * Description
 */

public class MusicMessage {
    String name;
    String artist;
    String poster;
    String artistId;
    long totalTime;
    long currentProgress;

    public MusicMessage(String name, String artist, String poster, String artistId, long totalTime, long currentProgress) {
        this.name = name;
        this.artist = artist;
        this.poster = poster;
        this.artistId = artistId;
        this.totalTime = totalTime;
        this.currentProgress = currentProgress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getPoster() {
        return poster;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public long getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(long currentProgress) {
        this.currentProgress = currentProgress;
    }
}
