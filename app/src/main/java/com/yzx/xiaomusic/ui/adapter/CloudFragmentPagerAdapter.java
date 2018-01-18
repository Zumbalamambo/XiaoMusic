package com.yzx.xiaomusic.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.utils.ResourceUtils;

import java.util.ArrayList;

/**
 * Created by yzx on 2018/1/12.
 * Description
 */

public class CloudFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseFragment> fragments;
    private ArrayList<Integer> titles;

    public CloudFragmentPagerAdapter(FragmentManager fm) {
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

    public void setDatas(ArrayList<BaseFragment> fragments, ArrayList<Integer> titles) {
        this.fragments = fragments;
        this.titles = titles;
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return ResourceUtils.parseString(titles.get(position));
    }
}
