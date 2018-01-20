package com.yzx.xiaomusic.ui.main;

import android.os.Bundle;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseActivity;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

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
    public FragmentAnimator onCreateFragmentAnimator() {
        return super.onCreateFragmentAnimator();
    }
}
