package com.yzx.xiaomusic.ui.main.cloud;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.ui.adapter.CloudFragmentPagerAdapter;
import com.yzx.xiaomusic.ui.main.cloud.music.ChildCloudFragment;
import com.yzx.xiaomusic.ui.main.cloud.radio.RadioCloudFragment;
import com.yzx.xiaomusic.ui.main.cloud.video.VideoCloudFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by yzx on 2018/1/12.
 * Description
 */

public class CloudFragment extends BaseFragment {


    private static CloudFragment cloudFragment;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private CloudFragmentPagerAdapter adapter;
    private ArrayList<BaseFragment> fragments;
    private ArrayList<Integer> titles;

    @SuppressLint("ValidFragment")
    private CloudFragment() {
    }

    public static CloudFragment getInstance(){
        if (cloudFragment == null){
            cloudFragment = new CloudFragment();
        }
        return cloudFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cloud;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        fragments = new ArrayList<>();
        fragments.add(ChildCloudFragment.getInstance());
        fragments.add(VideoCloudFragment.getInstance());
        fragments.add(RadioCloudFragment.getInstance());
        titles = new ArrayList();
        titles.add(R.string.smusic);
        titles.add(R.string.video);
        titles.add(R.string.radio);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        viewPager.setOffscreenPageLimit(3);
        adapter = new CloudFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        adapter.setDatas(fragments,titles);
        tabLayout.setupWithViewPager(viewPager);
    }
}
