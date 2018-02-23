package com.yzx.xiaomusic.cache;

import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;
import com.yzx.xiaomusic.common.Constants;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author yzx
 * @date 2018/2/23
 * Description
 */

public class CacheManager {

    private static final String TAG = "yglCacheManager";
    private static CacheManager cacheManager;
    /**
     * 音乐缓存500M
     */
    public static final int MAX_MUSIC_CACHE = 500 * 1024 * 1024;
    /**
     * 歌词缓存10M
     */
    public static final int MAX_LYRIC_CACHE = 10 * 1024 * 1024;
    /**
     * 图片缓存100M
     */
    public static final int MAX_IMG_CACHE = 100 * 1024 * 1024;
    private DiskLruCache musicCache;
    private DiskLruCache imgCache;
    private DiskLruCache lyricCache;

    private CacheManager() {
    }

    /**
     * 初始化一系列文件夹
     */
    private void initDirectory() {
        //应用目录
        File appDir = new File(Constants.PATH_APP);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        //缓存目录
        File cacheDir = new File(Constants.PATH_ABSOLUTE_CACHE);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    private void initMusicCache() {
        File musicCacheDir = new File(Constants.PATH_ABSOLUTE_CACHE_MUSIC);
        if (!musicCacheDir.exists()) {
            musicCacheDir.mkdirs();
        }

        File imgCacheDir = new File(Constants.PATH_ABSOLUTE_CACHE_IMG);
        if (!imgCacheDir.exists()) {
            imgCacheDir.mkdirs();
        }
        File lyricCacheDir = new File(Constants.PATH_ABSOLUTE_CACHE_LYRIC);
        if (!lyricCacheDir.exists()) {
            lyricCacheDir.mkdirs();
        }

        try {
            musicCache = DiskLruCache.open(musicCacheDir, 1, 1, MAX_MUSIC_CACHE);
            imgCache = DiskLruCache.open(imgCacheDir, 1, 1, MAX_IMG_CACHE);
            lyricCache = DiskLruCache.open(lyricCacheDir, 1, 1, MAX_LYRIC_CACHE);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "initMusicCache: "+e.toString());
        }
    }

    public static CacheManager getCacheManager(){
        if (cacheManager==null){
            cacheManager = new CacheManager();
        }
        return cacheManager;
    }

    public void init(){
        initDirectory();
        initMusicCache();
    }
    public DiskLruCache getMusicCache() {
        return musicCache;
    }

    public DiskLruCache getLyricCache() {
        return lyricCache;
    }

    public DiskLruCache getImgCache() {
        return imgCache;
    }
}
