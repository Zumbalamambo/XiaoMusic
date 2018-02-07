package com.yzx.xiaomusic.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.entities.ArtistCenterInfo;
import com.yzx.xiaomusic.entities.BufferInfo;
import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.entities.MusicMessage;
import com.yzx.xiaomusic.entities.PlayEvent;
import com.yzx.xiaomusic.entities.ProgressInfo;
import com.yzx.xiaomusic.entities.SongSheetDetials;
import com.yzx.xiaomusic.network.NetWorkUtils;
import com.yzx.xiaomusic.utils.JsonUtils;
import com.yzx.xiaomusic.utils.MusicDataUtils;
import com.yzx.xiaomusic.utils.PreferenceUtil;
import com.yzx.xiaomusic.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 *
 * @author yzx
 * @date 2018/1/22
 * Description  播放服务
 */

public class PlayService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnSeekCompleteListener {

    private static final String TAG = "yglPlayService";
    private static final String MUSIC_CURRENT_TIME = "currentTime";
    private  static final String MUSIC_PLAY_STATE = "playState";
    private static final String MUSIC_POSTION_IN_LIST ="positionInList";
    private static final String MUSIC_LIST = "musicList";//歌单
    private static final String MUSIC_INFO = "musicInfo";//音乐信息
    private static final String MUSIC_TYPE = "musicTYpe";

    public static final int STATE_BEFORE_PREPARED =0;//准备之前
    public static final int STATE_PLAYING =1;//正在播放
    public static final int STATE_PAUSE =2;//暂停
    public static final int STATE_IDLE =3;//其他状态

    private int state = STATE_IDLE;
    public MediaPlayer mediaPlayer=new MediaPlayer();

    private int progress;//歌曲播放进度

    private Object musicInfo;


    private int playListPosition;//当前歌单position，用来播放上一首下一首
    private MediaSessionManager mediaSessionManager;
    private Disposable disposable;


    @Override
    public void onCreate() {
        super.onCreate();
        mediaSessionManager = new MediaSessionManager(this);

        //初始化上次音乐播放信息
        setProgress(PreferenceUtil.getInt(MUSIC_CURRENT_TIME,0));
        setState(PreferenceUtil.getInt(MUSIC_PLAY_STATE,STATE_IDLE));
        setPlayListPosition(PreferenceUtil.getInt(MUSIC_POSTION_IN_LIST,0));
        String musicInfo = PreferenceUtil.getString(MUSIC_INFO, null);
        String musicList = PreferenceUtil.getString(MUSIC_LIST, null);
        if (TextUtils.isEmpty(musicInfo)){
            ToastUtils.showToast(R.string.error_get_last_play_music_info,ToastUtils.TYPE_NOTICE);
        }else {
            switch (PreferenceUtil.getInt(MUSIC_TYPE,-1)){
                case MusicDataUtils.TYPE_LOCAL:
                    setMusicInfo(JsonUtils.stringToObject(musicInfo,MusicInfo.class));
                    break;
                case MusicDataUtils.TYPE_SONG_SHEET:
                    setMusicInfo(JsonUtils.stringToObject(musicInfo,SongSheetDetials.ResultBean.TracksBean.class));
                    break;
                case MusicDataUtils.TYPE_ARTIST_CENTER:
                    setMusicInfo(JsonUtils.stringToObject(musicInfo,ArtistCenterInfo.HotSongsBean.class));
                    break;
            }

        }
        //初始化音乐列表
        if (!TextUtils.isEmpty(musicList)){
            PlayServiceManager playServiceManager = PlayServiceManager.getInstance();
            switch (MusicDataUtils.getMusicType(getMusicInfo())){
                case MusicDataUtils.TYPE_LOCAL:
                    playServiceManager.setLocalMusicList(JsonUtils.stringToList(musicList,MusicInfo.class));
                    break;
                case MusicDataUtils.TYPE_SONG_SHEET:
                    playServiceManager.setSongSheetMusicList(JsonUtils.stringToList(musicList,SongSheetDetials.ResultBean.TracksBean.class));
                    break;
                case MusicDataUtils.TYPE_ARTIST_CENTER:
                    playServiceManager.setArtistCenterMusicList(JsonUtils.stringToList(musicList,ArtistCenterInfo.HotSongsBean.class));
                    break;
            }
        }
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return new PlayBinder();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        next();
    }

    /**
     * 外部调用播放
     */
    public void  playMusic(){
        Log.i(TAG, "playMusic: "+state);
        switch (state){
            case STATE_BEFORE_PREPARED:
                play();
                break;
            case STATE_PLAYING:
                pause();
                break;
            case STATE_PAUSE:
                play();
                break;
            case STATE_IDLE:
                start();
                break;
        }
    }

    /**
     * 开始播放
     */
    public void start() {

        if (musicInfo instanceof MusicInfo){//本地歌曲
            List<MusicInfo> localMusicList = PlayServiceManager.getInstance().getLocalMusicList();
            if (localMusicList!=null&&localMusicList.size()>0){
                MusicInfo musicInfo;
                if (getPlayListPosition()>localMusicList.size()-1){
                    setPlayListPosition(0);
                    musicInfo = localMusicList.get(0);
                }else if (getPlayListPosition()<0){
                    musicInfo = localMusicList.get(localMusicList.size()-1);
                    setPlayListPosition(localMusicList.size()-1);
                }else {
                    musicInfo = localMusicList.get(getPlayListPosition());
                }
                mediaSessionManager.updatePlaybackState();
                setMusicInfo(musicInfo);
                playLocalMusic(musicInfo.getPath());
            }else {
                ToastUtils.showToast("本地歌单异常",ToastUtils.TYPE_NOTICE);
            }
        }else if (musicInfo instanceof SongSheetDetials.ResultBean.TracksBean){
            List<SongSheetDetials.ResultBean.TracksBean> songSheetMusicList = PlayServiceManager.getInstance().getSongSheetMusicList();
            if (songSheetMusicList!=null&&songSheetMusicList.size()>0){

                SongSheetDetials.ResultBean.TracksBean tracksBean;
                if (getPlayListPosition()>songSheetMusicList.size()-1){
                    setPlayListPosition(0);
                    tracksBean = songSheetMusicList.get(0);
                }else if (getPlayListPosition()<0){
                    tracksBean = songSheetMusicList.get(songSheetMusicList.size()-1);
                    setPlayListPosition(songSheetMusicList.size()-1);
                }else {
                    tracksBean = songSheetMusicList.get(getPlayListPosition());
                }
//                mediaSessionManager.updateMetaData(new MusicMessage(getMusicName(),getArtist(),getPoster(), getArtistId(),getDuration(), getProgress()));
                mediaSessionManager.updatePlaybackState();
                setMusicInfo(tracksBean);
                playNetMusic(tracksBean.getName(),String.valueOf(tracksBean.getId()));
            }else {
                ToastUtils.showToast("歌单异常",ToastUtils.TYPE_NOTICE);
            }
        }else if (musicInfo instanceof ArtistCenterInfo.HotSongsBean){
            List<ArtistCenterInfo.HotSongsBean> hotSongs = PlayServiceManager.getInstance().getHotSongs();
            if (hotSongs!=null&&hotSongs.size()>0) {

                ArtistCenterInfo.HotSongsBean hotSongsBean;
                if (getPlayListPosition() > hotSongs.size() - 1) {
                    setPlayListPosition(0);
                    hotSongsBean = hotSongs.get(0);
                } else if (getPlayListPosition() < 0) {
                    hotSongsBean = hotSongs.get(hotSongs.size() - 1);
                    setPlayListPosition(hotSongs.size() - 1);
                } else {
                    hotSongsBean = hotSongs.get(getPlayListPosition());
                }
//                mediaSessionManager.updateMetaData(new MusicMessage(getMusicName(),getArtist(),getPoster(), getArtistId(),getDuration(), getProgress()));

                mediaSessionManager.updatePlaybackState();
                setMusicInfo(hotSongsBean);
                playNetMusic(hotSongsBean.getName(), String.valueOf(hotSongsBean.getId()));
            }
        }
    }

    /**
     * 继续播放
     */
    public void play() {
        mediaPlayer.seekTo(progress);
        setState(STATE_PLAYING);
        mediaSessionManager.updatePlaybackState();
    }

    /**
     * 暂停
     */
    public void pause() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            EventBus.getDefault().post(new PlayEvent(PlayEvent.TYPE_PAUSE,null));
            progress = mediaPlayer.getCurrentPosition();
            setState(STATE_PAUSE);
            stopUpdataProgress();
            mediaSessionManager.updatePlaybackState();
        }
    }

    /**
     * 上一曲
     */
    public void previous(){
        setPlayListPosition(getPlayListPosition()-1);
        start();
    }

    /**
     * 下一曲
     */
    public void next(){
        setPlayListPosition(getPlayListPosition()+1);
        start();
    }

    /**
     * 播放网络音乐
     */
    private void playNetMusic(String name, String musicId) {
        Log.i(TAG, "playNetMusic: "+musicId);
        if (!TextUtils.isEmpty(musicId)){
            String pathname = MusicDataUtils.getMusicPath(name,musicId);
            File file = new File(pathname);
            if (file.exists()){
                playLocalMusic(pathname);
            }else {
                if (NetWorkUtils.isWifiConnected()){//wifi可用，获取歌曲
                    MusicAddressModel.getInstance().getMusicAddress(name,musicId);
                }else {//下一曲
                    ToastUtils.showToast(name+"未缓存，跳过该歌曲",ToastUtils.TYPE_NOTICE);
                    next();
                }
            }
        }else {
            ToastUtils.showToast("播放网络音乐异常，id为空",ToastUtils.TYPE_NOTICE);
        }

    }

    /**
     * 播放网络音乐
     * @param url
     */
    public void playCloudMusic(String url){
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(getBaseContext(),Uri.parse(url));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放本地音乐
     * @param path
     */
    private void playLocalMusic(String path) {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(final MediaPlayer mp) {
        state = STATE_BEFORE_PREPARED;
        if (isPrepared()){
            mp.start();
            //保存数据
            saveCurrentMusicInfo();

            startUpdataProgress();
            //更新数据
            setState(STATE_PLAYING);
            //更新数据
            EventBus.getDefault().post(new PlayEvent(PlayEvent.TYPE_CHANGE,
                    new MusicMessage(MusicDataUtils.getMusicId(getMusicInfo()),
                                    MusicDataUtils.getMusicName(getMusicInfo()),
                                    MusicDataUtils.getMusicArtist(getMusicInfo()),
                                    MusicDataUtils.getMusicPoster(getMusicInfo()),
                                    MusicDataUtils.getMusicArtistId(getMusicInfo()),
                                    MusicDataUtils.getMusicDuration(getMusicInfo()),
                    getProgress())));
            EventBus.getDefault().post(new PlayEvent(PlayEvent.TYPE_PLAY,null));
        }
    }

    /**
     * 更新进度条
     */
    private void startUpdataProgress() {
        Observable
                .interval(500, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        EventBus.getDefault().post(new PlayEvent(PlayEvent.TYPE_PROCESS,new ProgressInfo( MusicDataUtils.getMusicDuration(getMusicInfo())
                                ,mediaPlayer.getCurrentPosition())));
                    }
                });
    }

    /**
     * 停止更新
     */
    private void stopUpdataProgress() {
        if (disposable!=null){
            disposable.dispose();
        }
    }

    public boolean isPrepared(){
        return state ==STATE_BEFORE_PREPARED;
    }
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
//        Log.i(TAG, "onBufferingUpdate: "+percent);
        EventBus.getDefault().post(new PlayEvent(PlayEvent.TYPE_BUFFER,new BufferInfo(MusicDataUtils.getMusicDuration(getMusicInfo()),percent)));
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        progress =mp.getCurrentPosition();
        setState(STATE_PLAYING);
        mp.start();
        startUpdataProgress();
        mediaSessionManager.updatePlaybackState();
        EventBus.getDefault().post(new PlayEvent(PlayEvent.TYPE_PLAY,null));
    }

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }


    public int getPlayListPosition() {
//        Log.i(TAG, "getPlayListPosition: "+playListPosition);
        return playListPosition;
    }

    public void setPlayListPosition(int playListPosition) {
        this.playListPosition = playListPosition;
    }
    /**
     * 保存播放信息到本地
     */
    private void saveCurrentMusicInfo() {
        PreferenceUtil.put(MUSIC_CURRENT_TIME, getProgress());
        PreferenceUtil.put(MUSIC_INFO,JsonUtils.objectToString(getMusicInfo()));
        PreferenceUtil.put(MUSIC_PLAY_STATE,getState());
        PreferenceUtil.put(MUSIC_POSTION_IN_LIST,getPlayListPosition());
        PlayServiceManager playServiceManager = PlayServiceManager.getInstance();
        switch (MusicDataUtils.getMusicType(getMusicInfo())){
            case MusicDataUtils.TYPE_LOCAL:
                PreferenceUtil.put(MUSIC_LIST, JsonUtils.objectToString(playServiceManager.getLocalMusicList()));
                PreferenceUtil.put(MUSIC_TYPE,MusicDataUtils.TYPE_LOCAL);
                break;
            case MusicDataUtils.TYPE_SONG_SHEET:
                PreferenceUtil.put(MUSIC_LIST, JsonUtils.objectToString(playServiceManager.getSongSheetMusicList()));
                PreferenceUtil.put(MUSIC_TYPE,MusicDataUtils.TYPE_SONG_SHEET);
                break;
            case MusicDataUtils.TYPE_ARTIST_CENTER:
                PreferenceUtil.put(MUSIC_LIST, JsonUtils.objectToString(playServiceManager.getHotSongs()));
                PreferenceUtil.put(MUSIC_TYPE,MusicDataUtils.TYPE_ARTIST_CENTER);
                break;
        }
    }

    public boolean isPlaying() {
        return state ==STATE_PLAYING;
    }

    public boolean isPreparing() {
        return state==STATE_PLAYING;
    }

    public void setMusicInfo(Object musicInfo) {
        this.musicInfo = musicInfo;
    }

    public Object getMusicInfo() {
        return musicInfo;
    }

    public class PlayBinder extends Binder{

        public PlayService getService(){
            return PlayService.this;
        }
    }
}
