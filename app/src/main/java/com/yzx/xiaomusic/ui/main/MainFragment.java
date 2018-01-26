package com.yzx.xiaomusic.ui.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.service.PlayEvent;
import com.yzx.xiaomusic.ui.adapter.MainFragmentPagerAdapter;
import com.yzx.xiaomusic.ui.main.cloud.CloudFragment;
import com.yzx.xiaomusic.ui.main.friend.FriendFragment;
import com.yzx.xiaomusic.ui.main.music.MusicFragment;
import com.yzx.xiaomusic.ui.play.PlayFragment;
import com.yzx.xiaomusic.utils.GlideUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yzx.xiaomusic.service.PlayService.STATE_PLAYING;

/**
 * Created by yzx on 2018/1/19.
 * Description
 */

public class MainFragment extends BaseFragment implements ViewPager.OnPageChangeListener{
    private static final String TAG = "yglMainFragment";
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
    @BindView(R.id.iv_music_poster)
    ImageView ivMusicPoster;
    @BindView(R.id.tv_music_name)
    TextView tvMusicName;
    @BindView(R.id.tv_music_artist)
    TextView tvMusicArtist;
    @BindView(R.id.iv_music_play)
    ImageView ivMusicPlay;
    @BindView(R.id.iv_music_menu)
    ImageView ivMusicMenu;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
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

    @OnClick({R.id.rb_music, R.id.rb_cloud, R.id.rb_friend,R.id.iv_music_play, R.id.iv_music_menu,R.id.layout_music_control})
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
            case R.id.iv_music_play:
                getPlayService().playMusic();
                break;
            case R.id.iv_music_menu:
                break;
            case R.id.layout_music_control:
                start(PlayFragment.getInstance());
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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        setUpBottomPlayControl(tvMusicName,tvMusicArtist,ivMusicPlay,ivMusicPoster);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlayEvent event) {
        switch (event.type){
            case PlayEvent.TYPE_CHANGE:
                tvMusicName.setText(getPlayService().getMusicName());
                tvMusicArtist.setText(getPlayService().getArtist());
                ivMusicPlay.setImageResource(getPlayService().getState()==STATE_PLAYING? R.drawable.ic_bottom_play:R.drawable.ic_bottom_pause);
                GlideUtils.loadImg(context,getPlayService().getPoster(),GlideUtils.TYPE_DEFAULT,ivMusicPoster);
                break;
            case PlayEvent.TYPE_PLAY:
                ivMusicPlay.setImageResource(R.drawable.ic_bottom_play);
                break;
            case PlayEvent.TYPE_PAUSE:
                ivMusicPlay.setImageResource(R.drawable.ic_bottom_pause);
                break;
            default:
                break;
        }

    };
}
