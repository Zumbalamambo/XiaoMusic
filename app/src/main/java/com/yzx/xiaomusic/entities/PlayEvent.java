package com.yzx.xiaomusic.entities;

/**
 * Created by yzx on 2018/1/26.
 * Description
 */

public class PlayEvent {

    public static final int TYPE_CHANGE =0;
    public static final int TYPE_PLAY =1;
    public static final int TYPE_PAUSE =2;
    public static final int TYPE_PROCESS =3;
    public static final int TYPE_BUFFER =4;//缓存进度

    public PlayEvent(int type, Object data) {
        this.type = type;
        this.data = data;
    }

    public int type;
    public Object data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

