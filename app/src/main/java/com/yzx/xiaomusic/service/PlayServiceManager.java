package com.yzx.xiaomusic.service;

import com.yzx.xiaomusic.MusicApplication;
import com.yzx.xiaomusic.entities.CommonMusicInfo;
import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.entities.SongSheetDetials;

import java.util.List;

/**
 * Created by yzx on 2018/1/23.
 * Description  playService管理
 */

public class PlayServiceManager {

    private static PlayServiceManager playServiceManager;
    private PlayService playService;
    private MusicApplication.PlayServiceConnection serviceConnection;
    private List<MusicInfo> localMusicList;
    private List<SongSheetDetials.ResultBean.TracksBean> songSheetMusicList;
    private PlayServiceManager() {
    }

    public static PlayServiceManager getInstance(){
        if (playServiceManager==null){
            playServiceManager = new PlayServiceManager();
        }
        return playServiceManager;
    }

    public void setPlayService(PlayService playService){
        this.playService = playService;
    }

    public PlayService getPlayService() {
        return playService;
    }

    public boolean checkPlayServiceAlive(){
        if (playService==null){
            return false;
        }
        return true;
    }

    public void setPlayServiceConnection(MusicApplication.PlayServiceConnection serviceConnection) {
        this.serviceConnection = serviceConnection;
    }

    public MusicApplication.PlayServiceConnection getServiceConnection() {
        return serviceConnection;
    }

    public List<MusicInfo> getLocalMusicList() {
        return localMusicList;
    }

    public void setLocalMusicList(List<MusicInfo> localMusicList) {
        this.localMusicList = localMusicList;
    }

    public List<SongSheetDetials.ResultBean.TracksBean> getSongSheetMusicList() {
        return songSheetMusicList;
    }

    public void setSongSheetMusicList(List<SongSheetDetials.ResultBean.TracksBean> songSheetMusicList) {
        this.songSheetMusicList = songSheetMusicList;
    }

    /**
     * 设置音乐信息
     * @param commonMusicInfo
     */
    public void setCommonMusicInfo(CommonMusicInfo commonMusicInfo) {

    }
}
