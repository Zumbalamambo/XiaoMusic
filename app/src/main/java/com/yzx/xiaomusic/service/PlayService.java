package com.yzx.xiaomusic.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.entities.MusicAddress;
import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.utils.ToastUtils;

import java.io.IOException;

/**
 *
 * @author yzx
 * @date 2018/1/22
 * Description  播放服务
 */

public class PlayService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener {

    public static final int TYPE_LOCAL =1;
    public static final int TYPE_NET =2;
    public static final int STATE_BEFORE_PREPARED =0;//准备之前
    public static final int STATE_PREPARED =1;//准备
    public static final int STATE_PLAYING =2;//正在播放
    public static final int STATE_PAUSE =3;//暂停
    public static final int STATE_STOP =4;//停止

    private int state = STATE_BEFORE_PREPARED;//初始化未准备
    private static final String TAG = "yglPlayService";
    MediaPlayer mediaPlayer=new MediaPlayer();

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return new PlayBinder();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        Log.i(TAG, "onCompletion: ");
    }

    public void play(Object musicInfo,int type){
        if (mediaPlayer.isPlaying()){//在播放
            mediaPlayer.pause();
            state = STATE_PAUSE;
        }else {
            mediaPlayer.reset();
            try {
                if (type==TYPE_LOCAL){
                    mediaPlayer.setDataSource(((MusicInfo)musicInfo).path);
                }else {

                    MusicAddress musicAddress = (MusicAddress) musicInfo;
                    Log.i(TAG, "play: "+musicAddress.getData().get(0).getUrl());
                    if (musicAddress.getData().size()>0){
                        mediaPlayer.setDataSource(getBaseContext(), Uri.parse(musicAddress.getData().get(0).getUrl()));
                    }else {
                        ToastUtils.showToast(R.string.noThisMusic,ToastUtils.TYPE_FAIL);
                    }
                }
                mediaPlayer.prepareAsync();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        state = STATE_BEFORE_PREPARED;
        mp.start();
        state =STATE_PLAYING;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.i(TAG, "onBufferingUpdate: "+percent);
    }

    public class PlayBinder extends Binder{

        public PlayService getService(){
            return PlayService.this;
        }
    }
}
