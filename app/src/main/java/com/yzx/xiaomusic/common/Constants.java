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
     * 本地音乐
     */
    public static final int TYPE_MUSIC_LOCAL = 1;
    /**
     * 歌单音乐
     */
    public static final int TYPE_MUSIC_SONGSHEET = 2;
    /**
     * 歌手Id
     */
    public static final String KEY_ARTIST_ID = "artistId";

    /**
     * App缓存路径
     */
    public static final String PATH_APP = Environment.getExternalStorageDirectory().getAbsolutePath()+"/XiaoMusic";

    /**
     * 歌词目录
     */
    public static final String LYRIC ="/lyric";

    public static final String PATH_ABSOLUTE_LYRIC =PATH_APP+LYRIC;
}
