package com.yzx.xiaomusic.entities;

/**
 * Created by yzx on 2018/2/7.
 * Description
 */

public class BufferInfo {



    long duration;
    int progress;

    public BufferInfo(long duration, int progress) {
        this.duration = duration;
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
