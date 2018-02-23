package com.yzx.xiaomusic.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.yzx.xiaomusic.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by yzx on 2017/12/8.
 * Description Glide工具类
 */

public class GlideUtils {
    public static final int TYPE_DEFAULT = 0;
    //头像
    public static final int TYPE_HEAD = 1;
    //播放页面海报
    public static final int TYPE_PLAY_POSTER = 2;
    //歌手中心
    public static final int TYPE_ARTIST_CENTER = 3;
    //播放页面背景
    public static final int TYPE_PLAY_BG = 4;

    //加载圆形图片
    public static final int TRANSFORM_CIRCLE = 1;
    //加载模糊图片
    public static final int TRANSFORM_BLUR = 2;

    public static void loadImg(Context context, Object object, ImageView imageView) {
        loadImg(context, object, TYPE_DEFAULT, imageView);
    }

    public static void loadImg(Context context, Object object, int type, ImageView imageView) {
        loadImg(context, object, type, -1, imageView);
    }

    public static void loadImg(Context context, Object object, int type, int transformType, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        switch (type) {
            case TYPE_DEFAULT:
                options.placeholder(R.drawable.zhanweitu).error(R.drawable.zhanweitu);
                break;
            case TYPE_PLAY_POSTER:
                options.placeholder(R.drawable.ic_zhanweitu_play_poster).error(R.drawable.ic_zhanweitu_play_poster);
                break;
            case TYPE_PLAY_BG:
                options.placeholder(R.drawable.ic_zwt_play_bg).error(R.drawable.ic_zwt_play_bg);
                break;
            case TYPE_ARTIST_CENTER:
                options.placeholder(R.drawable.ic_zwt_artist_center).error(R.drawable.ic_zwt_artist_center);
                break;
        }

        switch (transformType) {
            case TRANSFORM_CIRCLE:
                options.circleCrop();
                options.transform(new CropCircleTransformation());
                break;
            case TRANSFORM_BLUR:
                options.transform(new BlurTransformation( 10, 40));
                break;
        }



        Glide.with(context)
                .asBitmap()
                .transition(BitmapTransitionOptions.withCrossFade())
                .load(object)
                .apply(options)
                .into(imageView);
    }
}
