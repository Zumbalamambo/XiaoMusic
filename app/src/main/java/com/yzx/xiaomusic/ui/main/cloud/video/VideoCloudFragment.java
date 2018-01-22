package com.yzx.xiaomusic.ui.main.cloud.video;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;

import butterknife.BindView;


/**
 * Created by yzx on 2018/1/12.
 * Description 视频页面
 */

public class VideoCloudFragment extends BaseFragment {

    private static VideoCloudFragment videoCloudFragment;
    @BindView(R.id.recyclerView)
    XRecyclerView recyclerView;

    @SuppressLint("ValidFragment")
    private VideoCloudFragment() {
    }

    public static VideoCloudFragment getInstance() {
        if (videoCloudFragment == null) {
            videoCloudFragment = new VideoCloudFragment();
        }
        return videoCloudFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cloud_video;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

}
