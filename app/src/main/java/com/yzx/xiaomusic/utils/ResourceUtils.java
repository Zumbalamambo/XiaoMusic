package com.yzx.xiaomusic.utils;

import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.yzx.xiaomusic.MusicApplication;


/**
 * Created by yzx on 2017/12/1.
 */

public class ResourceUtils {

    /**
     * 获取资源管理器
     * @return
     */
    public static Resources getResources(){
        return MusicApplication.getApplication().getResources();
    }

    /**
     * 颜色转换
     * @param color
     * @return
     */
    public static int parseColor(@ColorRes int color){

        return getResources().getColor(color);
    }

    /**
     *
     * @param string
     * @return
     */
    public static String parseString(@StringRes int string){

        return getResources().getString(string);
    }
}
