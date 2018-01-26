package com.yzx.xiaomusic;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.yzx.xiaomusic.service.PlayService;
import com.yzx.xiaomusic.service.PlayServiceManager;

import me.yokeyword.fragmentation.Fragmentation;

/**
 *
 * @author yzx
 * @date 2018/1/10
 * Description MusicApplication
 */

public class MusicApplication extends Application {

    private static final String TAG = "yglMusicApplication";
    public static MusicApplication musicApplication;
    public PlayService playService;
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

        //如果playService死了，再初始化PlayService
        if (!PlayServiceManager.getInstance().checkPlayServiceAlive()){

            Intent playServiceIntent = new Intent(this,PlayService.class);
            PlayServiceConnection serviceConnection=new PlayServiceConnection();
            PlayServiceManager.getInstance().setPlayServiceConnection(serviceConnection);
            bindService(playServiceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }


    public class PlayServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            playService = ((PlayService.PlayBinder) service).getService();
            PlayServiceManager.getInstance().setPlayService(playService);
            Log.i(TAG, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: ");
        }
    }

    public static MusicApplication getApplication() {
        return musicApplication;
    }

}
