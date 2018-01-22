package com.yzx.xiaomusic.ui.play;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;

/**
 * Created by yzx on 2018/1/21.
 * Description 播放页面
 */

public class PlayFragment extends BaseFragment {

    private static PlayFragment playFragment;

    @SuppressLint("ValidFragment")
    private PlayFragment() {
    }

    public static PlayFragment getInstance(){
        if (playFragment == null){
            playFragment = new PlayFragment();
        }
        return playFragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_paly;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
