package com.yzx.xiaomusic.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yzx.xiaomusic.common.BaseFragment;

import java.util.ArrayList;

/**
 * Created by yzx on 2018/1/12.
 * Description
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> fragments;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    public void setFragmentDatas(ArrayList<BaseFragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }
}
