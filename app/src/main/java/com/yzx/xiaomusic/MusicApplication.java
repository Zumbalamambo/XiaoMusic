package com.yzx.xiaomusic;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

/**
 *
 * @author yzx
 * @date 2018/1/10
 * Description MusicApplication
 */

public class MusicApplication extends Application {

    public static MusicApplication musicApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        musicApplication = this;
//        Bmob.initialize(this, "492b528b11d9806258a71340ec1ad925");
        CrashReport.initCrashReport(getApplicationContext(), "21692abef2", true);
    }

    public static MusicApplication getApplication() {
        return musicApplication;
    }
}
