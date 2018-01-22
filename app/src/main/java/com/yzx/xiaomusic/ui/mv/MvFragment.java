package com.yzx.xiaomusic.ui.mv;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;

/**
 *
 * @author yzx
 * @date 2018/1/21
 * Description mv页面
 */

public class MvFragment extends BaseFragment {


    private static MvFragment mvFragment;

    @SuppressLint("ValidFragment")
    private MvFragment() {
    }

    public static MvFragment getInstance(){
        if (mvFragment == null){
            mvFragment = new MvFragment();
        }
        return mvFragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mv;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
