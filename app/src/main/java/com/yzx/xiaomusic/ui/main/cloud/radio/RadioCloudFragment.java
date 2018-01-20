package com.yzx.xiaomusic.ui.main.cloud.radio;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;


/**
 * Created by yzx on 2018/1/12.
 * Description
 */

public class RadioCloudFragment extends BaseFragment {


    private static RadioCloudFragment radioCloudFragment;

    @SuppressLint("ValidFragment")
    private RadioCloudFragment() {
    }

    public static RadioCloudFragment getInstance(){
        if (radioCloudFragment == null){
            radioCloudFragment = new RadioCloudFragment();
        }
        return radioCloudFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cloud_radio;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
