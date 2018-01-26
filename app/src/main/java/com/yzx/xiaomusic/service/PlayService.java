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
import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.entities.SongSheetDetials;
import com.yzx.xiaomusic.utils.PreferenceUtil;
import com.yzx.xiaomusic.utils.ResourceUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author yzx
 * @date 2018/1/22
 * Description  播放服务
 */

public class PlayService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnSeekCompleteListener {

    public static final int TYPE_LOCAL =1;
    public static final int TYPE_NET =2;



    private static final String MUSIC_NAME = "musicName";
    private static final String MUSIC_ARTIST = "artist";
    private static final String MUSIC_TOTOL_TIME = "totalTime";
    private static final String MUSIC_CURRENT_TIME = "currentTime";
    private static final String MUSIC_TYPE = "musicType";
    private static final String MUSIC_ADDRESS = "musicAddress";
    private static final String MUSIC_PLAY_STATE = "playState";

    public static final int STATE_BEFORE_PREPARED =0;//准备之前
    public static final int STATE_PLAYING =1;//正在播放
    public static final int STATE_PAUSE =2;//暂停
    public static final int STATE_IDLE =3;//其他状态

    private int state = STATE_IDLE;


    private static final String TAG = "yglPlayService";
    public MediaPlayer mediaPlayer=new MediaPlayer();

    private int mediaPlayerCurrentPosition;
    private long musicTotalTime;//歌曲总时长
    private String musicName;//歌名
    private String artist;//歌手
    private int musicType;//本地或者网络歌曲
    private String musicAddress;//歌曲路径
    private String md5;
    private String musicId;
    private String poster;//海报
    private int playListPosition;//当前歌单position，用来播放上一首下一首
    private MediaSessionManager mediaSessionManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mediaSessionManager = new MediaSessionManager(this);
        int musicType = PreferenceUtil.getInt(MUSIC_TYPE, 1);
        setMusicAddress(PreferenceUtil.getString(MUSIC_ADDRESS,null));
        setMusicName(PreferenceUtil.getString(MUSIC_NAME,null));
        setArtist(PreferenceUtil.getString(MUSIC_ARTIST,null));
        setMusicTotalTime(PreferenceUtil.getLong(MUSIC_TOTOL_TIME,0));
        setMediaPlayerCurrentPosition(PreferenceUtil.getInt(MUSIC_CURRENT_TIME,0));
        if(!TextUtils.isEmpty(getMusicAddress())){
            if (musicType==1){
                prepareLocalMusic();
            }else {
                prepareCloudMusic();
            }
        }
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
    }


    private void prepareCloudMusic() {
        try {
            mediaPlayer.setDataSource(getMusicAddress());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareLocalMusic() {
        try {
            mediaPlayer.setDataSource(getBaseContext(),Uri.parse(getMusicAddress()));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Log.i(TAG, "onCompletion: ");
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
        Log.i(TAG, "start: ");
        if (TYPE_LOCAL == musicType){
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
                mediaSessionManager.updateMetaData(new MusicMessage(getMusicName(),getArtist(),getPoster(),getMusicTotalTime(),getMediaPlayerCurrentPosition()));
                mediaSessionManager.updatePlaybackState();
                setPoster(musicInfo.getPoster());
                setMusicName(musicInfo.getName());
                setArtist(musicInfo.getArtist());
                setMusicTotalTime(musicInfo.getDuration());
                playLocalMusic(musicInfo);
            }
        }else if (TYPE_NET == musicType){
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
                mediaSessionManager.updateMetaData(new MusicMessage(getMusicName(),getArtist(),getPoster(),getMusicTotalTime(),getMediaPlayerCurrentPosition()));
                mediaSessionManager.updatePlaybackState();
                setPoster(tracksBean.getAlbum().getPicUrl());
                setMusicName(tracksBean.getName());
                setArtist(tracksBean.getArtists().size()>0?tracksBean.getArtists().get(0).getName(): ResourceUtils.parseString(R.string.unKnow));
                setMusicTotalTime(tracksBean.getDuration());
                playSongSheetMusic(tracksBean);
            }
        }
    }

    /**
     * 继续播放
     */
    public void play() {

        Log.i(TAG, "play: "+mediaPlayerCurrentPosition);
        mediaPlayer.seekTo(mediaPlayerCurrentPosition);
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
            mediaPlayerCurrentPosition = mediaPlayer.getCurrentPosition();
            Log.i(TAG, "pause: 暂停时进度："+mediaPlayerCurrentPosition);
            setState(STATE_PAUSE);
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
     * 播放歌单音乐
     * @param tracksBean
     */
    private void playSongSheetMusic(SongSheetDetials.ResultBean.TracksBean tracksBean) {

        MusicAddressModel.getInstance().getMusicAddress(String.valueOf(tracksBean.getId()));
    }

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
     * @param musicInfo
     */
    private void playLocalMusic(MusicInfo musicInfo) {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(musicInfo.path);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        state = STATE_BEFORE_PREPARED;
        if (isPrepared()){
            mp.start();
            //更新数据
            setState(STATE_PLAYING);
            EventBus.getDefault().post(new PlayEvent(PlayEvent.TYPE_CHANGE,new MusicMessage(getMusicName(),getArtist(),getPoster(),getMusicTotalTime(),getMediaPlayerCurrentPosition())));
        }
    }

    public boolean isPrepared(){
        return state ==STATE_BEFORE_PREPARED;
    }
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.i(TAG, "onBufferingUpdate: "+percent);
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        mediaPlayerCurrentPosition=mp.getCurrentPosition();
        setState(STATE_PLAYING);
        mp.start();
        mediaSessionManager.updatePlaybackState();
        EventBus.getDefault().post(new PlayEvent(PlayEvent.TYPE_PLAY,null));
    }



    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public void setMusicTotalTime(long musicTotalTime){
        this.musicTotalTime = musicTotalTime;
    }

    public long getMusicTotalTime() {
        return musicTotalTime;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getMediaPlayerCurrentPosition() {
        return mediaPlayerCurrentPosition;
    }

    public void setMediaPlayerCurrentPosition(int mediaPlayerCurrentPosition) {
        this.mediaPlayerCurrentPosition = mediaPlayerCurrentPosition;
    }

    public int getMusicType() {
        return musicType;
    }

    public void setMusicType(int musicType) {
        this.musicType = musicType;
    }

    public String getMusicAddress() {
        return musicAddress;
    }

    public void setMusicAddress(String musicAddress) {
        this.musicAddress = musicAddress;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getPlayListPosition() {
        return playListPosition;
    }

    public void setPlayListPosition(int playListPosition) {
        this.playListPosition = playListPosition;
    }
    @Override
    public void onDestroy() {
        saveCurrentMusicInfo();
        super.onDestroy();
    }

    /**
     * 保存播放信息到本地
     */
    private void saveCurrentMusicInfo() {
        PreferenceUtil.put(MUSIC_NAME,getMusicName());
        PreferenceUtil.put(MUSIC_ARTIST,getArtist());
        PreferenceUtil.put(MUSIC_TOTOL_TIME, getMusicTotalTime());
        PreferenceUtil.put(MUSIC_CURRENT_TIME,getMediaPlayerCurrentPosition());
        PreferenceUtil.put(MUSIC_TYPE,getMusicType());
        PreferenceUtil.put(MUSIC_ADDRESS,getMusicAddress());
        PreferenceUtil.put(MUSIC_PLAY_STATE,getState());
    }

    public boolean isPlaying() {
        return state ==STATE_PLAYING;
    }

    public boolean isPreparing() {
        return state==STATE_PLAYING;
    }

    public class PlayBinder extends Binder{

        public PlayService getService(){
            return PlayService.this;
        }
    }

}
