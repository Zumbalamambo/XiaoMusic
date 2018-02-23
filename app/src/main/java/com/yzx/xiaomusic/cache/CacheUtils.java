package com.yzx.xiaomusic.cache;

import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yzx on 2018/2/23.
 * Description
 */

public class CacheUtils {

    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final String TAG = "yglCacheUtils";

    /**
     * 缓存音乐文件
     * @param id
     * @param url
     * @param md5
     */
    public static void cacheMusic(final String id, final String url, String md5) {
        final DiskLruCache musicCache = CacheManager.getCacheManager().getMusicCache();

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    DiskLruCache.Editor editor = musicCache.edit(id);
                    OutputStream outputStream = editor.newOutputStream(0);
                    if (downloadUrlToStream(url,outputStream)){
                        editor.commit();//提交
                    }else {
                        editor.abort();////重复操作
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {

            }
        });

    }

    /**
     * Download a bitmap from a URL and write the content to an output stream.
     *
     * @param urlString The URL to fetch
     * @return true if successful, false otherwise
     */
    private static boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error in downloadBitmap - " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
            }
        }
        return false;
    }

}
