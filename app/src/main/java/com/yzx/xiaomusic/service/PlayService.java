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
import com.yzx.xiaomusic.utils.JsonUtils;
import com.yzx.xiaomusic.utils.PreferenceUtil;
import com.yzx.xiaomusic.utils.ResourceUtils;
import com.yzx.xiaomusic.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.yzx.xiaomusic.ui.main.music.local.LocalMusicModel.LOCAL_MUSIC_INFO;

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
    private  static final String MUSIC_PLAY_STATE = "playState";
    private static final String MUSIC_POSTION_IN_LIST ="positionInList";
    public static final int STATE_BEFORE_PREPARED =0;//准备之前
    public static final int STATE_PLAYING =1;//正在播放
    public static final int STATE_PAUSE =2;//暂停
    public static final int STATE_IDLE =3;//其他状态

    private int state = STATE_IDLE;

    private static final String TAG = "yglPlayService";
    public MediaPlayer mediaPlayer=new MediaPlayer();

    private int progress;//歌曲播放进度
    private long duration;//歌曲总时长
    private String musicName;//歌名
    private String artist;//歌手

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    private String artistId;//歌手id
    private int musicType;//本地或者网络歌曲
    private String musicAddress;//歌曲路径
    private String md5;
    private String musicId;
    private String poster;//海报
    private int playListPosition;//当前歌单position，用来播放上一首下一首
    private MediaSessionManager mediaSessionManager;
    private Disposable disposable;


    @Override
    public void onCreate() {
        super.onCreate();

        mediaSessionManager = new MediaSessionManager(this);
        setMusicType(PreferenceUtil.getInt(MUSIC_TYPE, 1));
        setMusicAddress(PreferenceUtil.getString(MUSIC_ADDRESS,null));
        setMusicName(PreferenceUtil.getString(MUSIC_NAME,null));
        setArtist(PreferenceUtil.getString(MUSIC_ARTIST,null));
        setDuration(PreferenceUtil.getLong(MUSIC_TOTOL_TIME,0));
        setProgress(PreferenceUtil.getInt(MUSIC_CURRENT_TIME,0));
        if (musicType==1){//设置本地音乐数据
            String localMusicList = PreferenceUtil.getString(LOCAL_MUSIC_INFO, null);
            if (!TextUtils.isEmpty(localMusicList)){
                List<MusicInfo> musicInfos = JsonUtils.stringToList(localMusicList, MusicInfo.class);
                PlayServiceManager.getInstance().setLocalMusicList(musicInfos);
            }else {
                ToastUtils.showToast("本地数据音乐列表获取异常");
            }

        }else {//

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
        Log.i(TAG, "start: "+getMusicType());
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
                mediaSessionManager.updateMetaData(new MusicMessage(getMusicName(),getArtist(),getPoster(),getArtistId(), getDuration(), getProgress()));
                mediaSessionManager.updatePlaybackState();
                setPoster(musicInfo.getPoster());
                setMusicName(musicInfo.getName());
                setArtist(musicInfo.getArtist());
                setDuration(musicInfo.getDuration());
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
                mediaSessionManager.updateMetaData(new MusicMessage(getMusicName(),getArtist(),getPoster(), getArtistId(),getDuration(), getProgress()));
                mediaSessionManager.updatePlaybackState();
                setPoster(tracksBean.getAlbum().getPicUrl());
                setMusicName(tracksBean.getName());
                setArtist(tracksBean.getArtists().size()>0?tracksBean.getArtists().get(0).getName(): ResourceUtils.parseString(R.string.unKnow));
                setDuration(tracksBean.getDuration());
                playSongSheetMusic(tracksBean);
            }
        }
    }

    /**
     * 继续播放
     */
    public void play() {

        Log.i(TAG, "play: "+ progress);
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
            Log.i(TAG, "pause: 暂停时进度："+ progress);
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
     * 播放歌单音乐
     * @param tracksBean
     */
    private void playSongSheetMusic(SongSheetDetials.ResultBean.TracksBean tracksBean) {

        MusicAddressModel.getInstance().getMusicAddress(String.valueOf(tracksBean.getId()));
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
    public void onPrepared(final MediaPlayer mp) {
        state = STATE_BEFORE_PREPARED;
        if (isPrepared()){
            mp.start();
            //保存数据
            saveCurrentMusicInfo();

            startUpdataProgress();
            //更新数据
            setState(STATE_PLAYING);
            EventBus.getDefault().post(new PlayEvent(PlayEvent.TYPE_CHANGE,new MusicMessage(getMusicName(),getArtist(),getPoster(),getArtistId(), getDuration(), getProgress())));
            EventBus.getDefault().post(new PlayEvent(PlayEvent.TYPE_PLAY,null));
        }
    }

    /**
     * 更新进度条
     */
    private void startUpdataProgress() {
        Observable
                .interval(500, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        EventBus.getDefault().post(new PlayEvent(PlayEvent.TYPE_PROCESS,new ProgressInfo(getDuration(),mediaPlayer.getCurrentPosition())));
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.i(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
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
        Log.i(TAG, "onBufferingUpdate: "+percent);
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
    public void setDuration(long duration){
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
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
    /**
     * 保存播放信息到本地
     */
    private void saveCurrentMusicInfo() {
        Log.i(TAG, "saveCurrentMusicInfo: "+getDuration());
        PreferenceUtil.put(MUSIC_NAME,getMusicName());
        PreferenceUtil.put(MUSIC_ARTIST,getArtist());
        PreferenceUtil.put(MUSIC_TOTOL_TIME,getDuration());
        PreferenceUtil.put(MUSIC_CURRENT_TIME, getProgress());
        PreferenceUtil.put(MUSIC_TYPE,getMusicType());
        PreferenceUtil.put(MUSIC_ADDRESS,getMusicAddress());
        PreferenceUtil.put(MUSIC_PLAY_STATE,getState());
        PreferenceUtil.put(MUSIC_POSTION_IN_LIST,getPlayListPosition());
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


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
