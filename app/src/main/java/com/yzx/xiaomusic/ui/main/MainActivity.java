package com.yzx.xiaomusic.ui.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseActivity;
import com.yzx.xiaomusic.service.PlayService;
import com.yzx.xiaomusic.service.PlayServiceManager;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    public PlayService playService;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        MainFragment fragment = findFragment(MainFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.layout_container, new MainFragment());
        }

        //如果playService死了，在绑定service
        if (!PlayServiceManager.getInstance().checkPlayServiceAlive()){

            Intent playServiceIntent = new Intent(context,PlayService.class);
            PlayServiceConnection serviceConnection=new PlayServiceConnection();
            bindService(playServiceIntent,serviceConnection, Context.BIND_AUTO_CREATE);

        }

    }

    class PlayServiceConnection implements ServiceConnection {

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
}
