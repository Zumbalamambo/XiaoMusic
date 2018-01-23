package com.yzx.xiaomusic.service;

/**
 * Created by yzx on 2018/1/23.
 * Description  playService管理
 */

public class PlayServiceManager {

    private static PlayServiceManager playServiceManager;
    private PlayService playService;

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
}
