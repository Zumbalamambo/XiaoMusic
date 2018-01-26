package com.yzx.xiaomusic.service;

/**
 * Created by yzx on 2018/1/26.
 * Description
 */

public class MusicMessage {
    String name;
    String artist;
    String posetr;
    long totalTime;
    long currentProgress;

    public MusicMessage(String name, String artist, String posetr, long totalTime, long currentProgress) {
        this.name = name;
        this.artist = artist;
        this.posetr = posetr;
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
    public String getPosetr() {
        return posetr;
    }

    public void setPosetr(String posetr) {
        this.posetr = posetr;
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
