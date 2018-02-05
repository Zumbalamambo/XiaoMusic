package com.yzx.xiaomusic.utils;

import android.content.ContentUris;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.shuyu.gsyvideoplayer.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by shuyu on 2016/11/11.
 */

public class CommonUtil {

    public static void setViewHeight(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (null == layoutParams)
            return;
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    public static void saveBitmap(Bitmap bitmap) throws FileNotFoundException {
        if (bitmap != null) {
            File file = new File(FileUtils.getPath(), "GSY-" + System.currentTimeMillis() + ".jpg");
            OutputStream outputStream;
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            bitmap.recycle();
        }
    }

    private static final Uri sArtworkUri = Uri
            .parse("content://media/external/audio/albumart");

    public static String getMusicBitemp(long songid,long albumid) {


        try {
            Uri uri;
            if (albumid < 0) {
                uri = Uri.parse("content://media/external/audio/media/" + songid + "/albumart");
            } else {
                uri = ContentUris.withAppendedId(sArtworkUri, albumid);
            }

            if (uri==null){
                return null;
            }
            uri.toString();
            return uri.toString();
        } catch (Exception ignored) {
        }

        return null;
    }
}
