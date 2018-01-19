package com.yzx.xiaomusic.ui.main.cloud;

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


    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private CloudFragmentPagerAdapter adapter;
    private ArrayList<BaseFragment> fragments;
    private ArrayList<Integer> titles;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cloud;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        fragments = new ArrayList<>();
        fragments.add(new ChildCloudFragment());
        fragments.add(new VideoCloudFragment());
        fragments.add(new RadioCloudFragment());
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
