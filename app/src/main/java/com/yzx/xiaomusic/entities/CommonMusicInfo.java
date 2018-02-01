package com.yzx.xiaomusic.entities;

import java.util.List;

/**
 * Created by yzx on 2018/1/31.
 * Description  服务里统一的音乐信息
 */

public class CommonMusicInfo {


    public CommonMusicInfo(String id, String md5, String name, String artist, String poster, int playState,
                           int musicTye, int positionInList, int process, long duration, List<MusicInfo> localMusicList,
                           List<SongSheetDetials.ResultBean.TracksBean> songSheetList) {
        this.id = id;
        this.md5 = md5;
        this.name = name;
        this.artist = artist;
        this.poster = poster;
        this.playState = playState;
        this.musicTye = musicTye;
        this.positionInList = positionInList;
        this.process = process;
        this.duration = duration;
        this.localMusicList = localMusicList;
        this.songSheetList = songSheetList;
    }

    /**
     * 音乐类型定义常量
     */

    String id;//id主要用于网络歌曲
    String md5;//md5
    String name;//歌名
    String artist;//歌手
    String poster;//海报
    int playState;//退出时的播放状态
    int musicTye;//音乐类型   1---本地音乐   2----网络音乐
    int positionInList;//该歌曲在歌单中的位置
    int process;//退出时播放进度
    long duration;//歌曲总时长
    List<MusicInfo> localMusicList; //本地音乐列表
    List<SongSheetDetials.ResultBean.TracksBean> songSheetList;//歌单列表

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

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
    public int getPlayState() {
        return playState;
    }

    public void setPlayState(int playState) {
        this.playState = playState;
    }

    public int getMusicTye() {
        return musicTye;
    }
    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
    public void setMusicTye(int musicTye) {
        this.musicTye = musicTye;
    }

    public int getPositionInList() {
        return positionInList;
    }

    public void setPositionInList(int positionInList) {
        this.positionInList = positionInList;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public List<MusicInfo> getLocalMusicList() {
        return localMusicList;
    }

    public void setLocalMusicList(List<MusicInfo> localMusicList) {
        this.localMusicList = localMusicList;
    }

    public List<SongSheetDetials.ResultBean.TracksBean> getSongSheetList() {
        return songSheetList;
    }

    public void setSongSheetList(List<SongSheetDetials.ResultBean.TracksBean> songSheetList) {
        this.songSheetList = songSheetList;
    }
}
