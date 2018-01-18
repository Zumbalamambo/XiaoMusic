package com.yzx.xiaomusic.ui.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.RadioButton;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseActivity;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.ui.adapter.MainFragmentPagerAdapter;
import com.yzx.xiaomusic.ui.main.cloud.CloudFragment;
import com.yzx.xiaomusic.ui.main.friend.FriendFragment;
import com.yzx.xiaomusic.ui.main.music.MusicFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.rb_music)
    RadioButton rbMusic;
    @BindView(R.id.rb_cloud)
    RadioButton rbCloud;
    @BindView(R.id.rb_friend)
    RadioButton rbFriend;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        viewPager.setOffscreenPageLimit(3);
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new MusicFragment());
        fragments.add(new CloudFragment());
        fragments.add(new FriendFragment());
        adapter.setFragmentDatas(fragments);
        viewPager.setAdapter(adapter);
    }

    @OnClick({R.id.rb_music, R.id.rb_cloud, R.id.rb_friend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_music:
                break;
            case R.id.rb_cloud:
                break;
            case R.id.rb_friend:
                break;
        }
    }


}
