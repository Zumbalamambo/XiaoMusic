package com.yzx.xiaomusic.utils;

import com.yzx.xiaomusic.entities.ArtistCenterInfo;
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
    public static final int TYPE_ARTIST_CENTER =3;

    /**
     * 获取音乐类型
     * @return
     */
    public static int getMusicType(Object musicInfo) {

        if (musicInfo==null){
            return -1;
        }
        if (musicInfo instanceof MusicInfo){
            return TYPE_LOCAL;
        }else if (musicInfo instanceof SongSheetDetials.ResultBean.TracksBean){
            return TYPE_SONG_SHEET;
        }else if (musicInfo instanceof ArtistCenterInfo.HotSongsBean){
            return TYPE_ARTIST_CENTER;
        }
        return -1;
    }

    public static Object getMusicInfo() {
       return PlayServiceManager.getInstance().getPlayService().getMusicInfo();
    }

    /**
     * 获取歌名
     */
    public static String getMusicName(Object musicInfo){
        if (musicInfo==null){
            return null;
        }
        switch (getMusicType(musicInfo)){
            case TYPE_LOCAL:
                return ((MusicInfo) musicInfo).getName();
            case TYPE_SONG_SHEET:
                return ((SongSheetDetials.ResultBean.TracksBean)musicInfo).getName();
            case TYPE_ARTIST_CENTER:
                return ((ArtistCenterInfo.HotSongsBean)musicInfo).getName();
            default:
                return null;
        }
    }

    /**
     * 获取歌手
     * @return
     */
    public static String getMusicArtist(Object musicInfo) {
        if (musicInfo==null){
            return null;
        }
        switch (getMusicType(musicInfo)){
            case TYPE_LOCAL:
                return ((MusicInfo) musicInfo).getArtist();
            case TYPE_SONG_SHEET:
                List<SongSheetDetials.ResultBean.TracksBean.ArtistsBeanX> artists = ((SongSheetDetials.ResultBean.TracksBean)musicInfo).getArtists();
                if (artists.size()>0){
                    return artists.get(0).getName();
                }
                    return null;

            case TYPE_ARTIST_CENTER:
                List<ArtistCenterInfo.HotSongsBean.ArBean> artistss = ((ArtistCenterInfo.HotSongsBean) musicInfo).getAr();
                if (artistss.size()>0){
                    return artistss.get(0).getName();
                }
                return null;
            default:
                return null;
        }
    }

    /**
     * 获取海报
     * @return
     */
    public static String getMusicPoster(Object musicInfo) {
        if (musicInfo==null){
            return null;
        }
        switch (getMusicType(musicInfo)){
            case TYPE_LOCAL:
                return ((MusicInfo) musicInfo).getPoster();
            case TYPE_SONG_SHEET:
                return ((SongSheetDetials.ResultBean.TracksBean)musicInfo).getAlbum().getPicUrl();
            case TYPE_ARTIST_CENTER:
                return ((ArtistCenterInfo.HotSongsBean)musicInfo).getAl().getPicUrl();
            default:
                return null;
        }
    }

    /**
     * 获取歌手id
     * @return
     */
    public static String getMusicArtistId(Object musicInfo) {
        if (musicInfo==null){
            return null;
        }
        switch (getMusicType(musicInfo)){
            case TYPE_LOCAL:
                return null;
            case TYPE_SONG_SHEET:
                List<SongSheetDetials.ResultBean.TracksBean.ArtistsBeanX> artists = ((SongSheetDetials.ResultBean.TracksBean)musicInfo).getArtists();
                if (artists.size()>0){
                    return String.valueOf(artists.get(0).getId());
                }else {
                    return null;
                }
            case TYPE_ARTIST_CENTER:
                List<ArtistCenterInfo.HotSongsBean.ArBean> artistss = ((ArtistCenterInfo.HotSongsBean) musicInfo).getAr();
                if (artistss.size()>0){
                    return String.valueOf(artistss.get(0).getId());
                }
                return null;
            default:
                return null;
        }
    }

    /**
     * 获取歌曲时长
     * @return
     */
    public static long getMusicDuration(Object musicInfo) {
        if (musicInfo==null){
            return 0;
        }
        switch (getMusicType(musicInfo)){
            case TYPE_LOCAL:
                return ((MusicInfo) musicInfo).getDuration();
            case TYPE_SONG_SHEET:
                return ((SongSheetDetials.ResultBean.TracksBean)musicInfo).getDuration();
            case TYPE_ARTIST_CENTER:
                return ((ArtistCenterInfo.HotSongsBean)musicInfo).getDt();
            default:
                return 0;
        }
    }
}
