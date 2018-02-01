package com.yzx.xiaomusic.ui.main;

import android.os.Bundle;

import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseActivity;

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
        //释放所有的视频资源
        GSYVideoPlayer.releaseAllVideos();
    }
}
