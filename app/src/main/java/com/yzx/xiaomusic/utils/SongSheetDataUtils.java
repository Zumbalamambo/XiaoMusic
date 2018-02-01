package com.yzx.xiaomusic.utils;

import com.yzx.xiaomusic.entities.SongSheetDetials;

import java.util.List;

/**
 * Created by yzx on 2018/2/1.
 * Description  处理歌单数据工具类
 */

public class SongSheetDataUtils {

    /**
     * 获取歌手
     * @param songSheetMusicInfo
     * @return
     */
    public static String getSongArtist(SongSheetDetials.ResultBean.TracksBean songSheetMusicInfo){
        List<SongSheetDetials.ResultBean.TracksBean.ArtistsBeanX> artists = songSheetMusicInfo.getArtists();
        if (artists.size()>0){
            return artists.get(0).getName();
        }else {
            return null;
        }
    }

    /**
     * 获取封面
     * @param songSheetMusicInfo
     * @return
     */
    public static String getSongPoster(SongSheetDetials.ResultBean.TracksBean songSheetMusicInfo) {

        return songSheetMusicInfo.getAlbum().getPicUrl();
    }
}
