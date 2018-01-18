package com.yzx.xiaomusic.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by yzx on 2017/6/21.
 * Description 获取网络状态工具类
 */

public class NetWorkUtils {

    private static final String TAG = "logNetWorkUtils";

    /**
     * 判断网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetAvailable(Context context){

        ConnectivityManager connectivityManager = getConnectivityManager(context);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo!=null&&activeNetworkInfo.isAvailable()){
            Log.d(TAG, "isNetAvailable: ");
            return true;
        }
        return false;
    }

    private static ConnectivityManager getConnectivityManager(Context context) {
        ConnectivityManager contextSystemService = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return contextSystemService;
    }
}
