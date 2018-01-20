package com.yzx.xiaomusic;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

import me.yokeyword.fragmentation.Fragmentation;

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
        Fragmentation.builder()
                // show stack view. Mode: BUBBLE, SHAKE, NONE
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install();
    }

    public static MusicApplication getApplication() {
        return musicApplication;
    }
}
