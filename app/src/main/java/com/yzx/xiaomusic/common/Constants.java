package com.yzx.xiaomusic.common;

import android.os.Environment;

/**
 * Created by yzx on 2018/1/17.
 * Description
 */

public class Constants {
//    http://music.163.com/api/playlist/list
    public static final String BASE_URL = "http://musicapi.leanapp.cn/";

    /**
     * 歌手Id
     */
    public static final String KEY_ARTIST_ID = "artistId";

    /**
     * App缓存路径
     */
    public static final String PATH_APP = Environment.getExternalStorageDirectory().getAbsolutePath()+"/xiaoMusic";

    /**
     * 缓存
     */
    public static final String CACHE ="/cache";
    /**
     * 歌词目录
     */
    public static final String LYRIC ="/lyric";
    /**
     * 歌曲缓存路径
     */
    public static final String MUSIC ="/music";
    /**
     * 图片缓存路径
     */
    public static final String IMG ="/img";
    /**
     * 歌曲缓存路径
     */
    public static final String DOWNLOAD ="/download";
    /**
     * 缓存绝对路径
     */
    public static final String PATH_ABSOLUTE_CACHE =PATH_APP+CACHE;

    /**
     * 歌词绝缓存对路径
     */
    public static final String PATH_ABSOLUTE_CACHE_LYRIC =PATH_APP+CACHE+LYRIC;

    /**
     * 歌曲缓存绝对路径
     */
    public static final String PATH_ABSOLUTE_CACHE_IMG =PATH_APP+CACHE+IMG;

    /**
     * 歌曲缓存绝对路径
     */
    public static final String PATH_ABSOLUTE_CACHE_MUSIC =PATH_APP+CACHE+MUSIC;

    /**
     * 歌曲下载绝对路径绝对路径
     */
    public static final String PATH_ABSOLUTE_DOWNLOAD =PATH_APP+DOWNLOAD;
}
