package com.yzx.xiaomusic.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

/**
 *
 * @author yzx
 * @date 2018/1/24
 * Description
 */

public class MarqueeView extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = "yglMarqueenView";

    public MarqueeView(Context context) {
        this(context,null);
    }

    public MarqueeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MarqueeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        int measuredWidth = getMeasuredWidth();
        CharSequence text = getText();
        Log.i(TAG, "init: ");

    }

}
