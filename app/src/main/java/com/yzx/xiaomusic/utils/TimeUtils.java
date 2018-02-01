package com.yzx.xiaomusic.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;

/**
 * Created by yzx on 2018/2/1.
 * Description
 */

public class TimeUtils {

    private static final String TAG = "yglTimeUtils";

    public static String parseTime(long data){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        String format = dateFormat.format(data);
        Log.i(TAG, "parseTime: "+format);
        return format;
    }
}
