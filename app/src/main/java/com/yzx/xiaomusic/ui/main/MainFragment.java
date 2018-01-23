package com.yzx.xiaomusic.ui.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.ui.adapter.MainFragmentPagerAdapter;
import com.yzx.xiaomusic.ui.main.cloud.CloudFragment;
import com.yzx.xiaomusic.ui.main.friend.FriendFragment;
import com.yzx.xiaomusic.ui.main.music.MusicFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yzx on 2018/1/19.
 * Description
 */

public class MainFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.rb_music)
    RadioButton rbMusic;
    @BindView(R.id.rb_cloud)
    RadioButton rbCloud;
    @BindView(R.id.rb_friend)
    RadioButton rbFriend;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.layout_music_control)
    LinearLayout layoutMusicControl;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initPlayWidget(layoutMusicControl);
        toolBar.setNavigationIcon(R.drawable.ic_menu);
        viewPager.setOffscreenPageLimit(3);
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getChildFragmentManager());
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(MusicFragment.getInstance());
        fragments.add(CloudFragment.getInstance());
        fragments.add(FriendFragment.getInstance());
        adapter.setFragmentDatas(fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    @OnClick({R.id.rb_music, R.id.rb_cloud, R.id.rb_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_music:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rb_cloud:
                viewPager.setCurrentItem(1);
                break;
            case R.id.rb_friend:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                rbMusic.setChecked(true);
                break;
            case 1:
                rbCloud.setChecked(true);
                break;
            case 2:
                rbFriend.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
