package com.yzx.xiaomusic.service;

/**
 * 进度信息，主要是用来设置音乐播放进度的
 */
public class ProgressInfo{
    public ProgressInfo(long duration, int process) {
        this.duration = duration;
        this.process = process;
    }

    long duration;
    int process;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }
}
