package com.yzx.xiaomusic.ui.main.music.local;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseActivity;
import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.ui.adapter.LocalMusicAdapter;
import com.yzx.xiaomusic.utils.ScanMusicUtils;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yzx on 2018/1/11.
 * Description 本地音乐文件
 */

public class LocalMusicActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static final String MUSIC_INFO = "musicInfo";
    @BindView(R.id.tv_scanning_music_name)
    TextView tvScanningMusicName;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    private List<MusicInfo> musicInfos;
    private LocalMusicPresenter mPresenter;
    public LocalMusicAdapter adapter;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_music_local;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        musicInfos = ScanMusicUtils.getLocalMusicInfoByPreference();
        mPresenter = new LocalMusicPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setSupportActionBar(toolBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new LocalMusicAdapter();
        recyclerView.setAdapter(adapter);
        if (musicInfos == null) {//
            mPresenter.getLocalMusicInfo(this);
            showScanAnimation();
        }
        adapter.setDatas(musicInfos);
        showActionBarTitle(R.string.localMusic);
        showBackArrow();
    }

    private void showScanAnimation() {
        tvScanningMusicName.animate().translationY(0).setDuration(0).setInterpolator(new AccelerateInterpolator()).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_music_local_scan, menu);
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if ("MenuBuilder".equals(menu.getClass().getSimpleName())) {
                try {
                    @SuppressLint("PrivateApi")
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                mPresenter.getLocalMusicInfo(context);
                showScanAnimation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
