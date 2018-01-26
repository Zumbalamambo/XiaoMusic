package com.yzx.xiaomusic.ui.main;

import android.os.Bundle;
import android.util.Log;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseActivity;
import com.yzx.xiaomusic.service.PlayServiceManager;

public class MainActivity extends BaseActivity {
    private static final String TAG = "yglMainActivity";


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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        unbindService(PlayServiceManager.getInstance().getServiceConnection());
    }
}
