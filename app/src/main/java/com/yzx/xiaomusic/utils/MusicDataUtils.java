package com.yzx.xiaomusic.utils;

import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.entities.SongSheetDetials;
import com.yzx.xiaomusic.service.PlayServiceManager;

import java.util.List;

/**
 * Created by yzx on 2018/2/1.
 * Description  处理歌单数据工具类
 */

public class MusicDataUtils {

    public static final int TYPE_LOCAL =1;
    public static final int TYPE_SONG_SHEET =2;

    /**
     * 获取音乐类型
     * @return
     */
    public static int getMusicType() {

        Object musicInfo = getMusicInfo();
        if (musicInfo==null){
            return -1;
        }
        if (musicInfo instanceof MusicInfo){
            return TYPE_LOCAL;
        }else if (musicInfo instanceof SongSheetDetials.ResultBean.TracksBean){
            return TYPE_SONG_SHEET;
        }
        return -1;
    }

    public static Object getMusicInfo() {
       return PlayServiceManager.getInstance().getPlayService().getMusicInfo();
    }

    /**
     * 获取歌名
     */
    public static String getMusicName(){
        Object musicInfo = getMusicInfo();
        if (musicInfo==null){
            return null;
        }
        switch (getMusicType()){
            case TYPE_LOCAL:
                return ((MusicInfo) musicInfo).getName();
            case TYPE_SONG_SHEET:
                return ((SongSheetDetials.ResultBean.TracksBean)musicInfo).getName();
            default:
                return null;
        }
    }

    /**
     * 获取歌手
     * @return
     */
    public static String getMusicArtist() {
        Object musicInfo = getMusicInfo();
        if (musicInfo==null){
            return null;
        }
        switch (getMusicType()){
            case TYPE_LOCAL:
                return ((MusicInfo) musicInfo).getArtist();
            case TYPE_SONG_SHEET:
                List<SongSheetDetials.ResultBean.TracksBean.ArtistsBeanX> artists = ((SongSheetDetials.ResultBean.TracksBean)musicInfo).getArtists();
                if (artists.size()>0){
                    return artists.get(0).getName();
                }else {
                    return null;
                }
            default:
                return null;
        }
    }

    /**
     * 获取歌手
     * @param songSheetMusicInfo
     * @return
     */
    public static String getSongArtistId(SongSheetDetials.ResultBean.TracksBean songSheetMusicInfo){
        List<SongSheetDetials.ResultBean.TracksBean.ArtistsBeanX> artists = songSheetMusicInfo.getArtists();
        if (artists.size()>0){
            return String.valueOf(artists.get(0).getId());
        }else {
            return null;
        }
    }


    /**
     * 获取海报
     * @return
     */
    public static String getMusicPoster() {
        Object musicInfo = getMusicInfo();
        if (musicInfo==null){
            return null;
        }
        switch (getMusicType()){
            case TYPE_LOCAL:
                return ((MusicInfo) musicInfo).getPoster();
            case TYPE_SONG_SHEET:
                return ((SongSheetDetials.ResultBean.TracksBean)musicInfo).getAlbum().getPicUrl();
            default:
                return null;
        }
    }

    /**
     * 获取歌曲id
     * @return
     */
    public static String getMusicArtistId() {
        Object musicInfo = getMusicInfo();
        if (musicInfo==null){
            return null;
        }
        switch (getMusicType()){
            case TYPE_LOCAL:
                return null;
            case TYPE_SONG_SHEET:
                return String.valueOf(((SongSheetDetials.ResultBean.TracksBean)musicInfo).getId());
            default:
                return null;
        }
    }

    /**
     * 获取歌曲时长
     * @return
     */
    public static long getMusicDuration() {
        Object musicInfo = getMusicInfo();
        if (musicInfo==null){
            return 0;
        }
        switch (getMusicType()){
            case TYPE_LOCAL:
                return ((MusicInfo) musicInfo).getDuration();
            case TYPE_SONG_SHEET:
                return ((SongSheetDetials.ResultBean.TracksBean)musicInfo).getDuration();
            default:
                return 0;
        }
    }
}
