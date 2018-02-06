package com.yzx.xiaomusic.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by yzx on 2018/2/6.
 * Description
 */

public class ActivityUtils {

    /**
     * 回到桌面
     * @param context
     */
    public static void backToDesk(Context context) {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(homeIntent);
    }

}
