package com.yzx.xiaomusic.ui.main.music.local;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.ui.adapter.LocalMusicAdapter;
import com.yzx.xiaomusic.ui.main.MainActivity;
import com.yzx.xiaomusic.utils.ScanMusicUtils;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;

/**
 *
 * @author yzx
 * @date 2018/1/19
 * Description
 */

public class LocalMusicFragment extends BaseFragment {
    private static LocalMusicFragment localMusicFragment;
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

    @SuppressLint("ValidFragment")
    private LocalMusicFragment() {
    }

    public static LocalMusicFragment getInstance(){
        if (localMusicFragment == null){
            localMusicFragment = new LocalMusicFragment();
        }
        return localMusicFragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_local;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        setToolBar(toolBar,R.string.localMusic);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LocalMusicAdapter();
        recyclerView.setAdapter(adapter);
        if (musicInfos == null) {//
            mPresenter.getLocalMusicInfo(getContext());
            showScanAnimation();
        }
        adapter.setDatas(musicInfos);
        ((MainActivity)getSupportDelegate().getActivity()).setSupportActionBar(toolBar);
        toolBar.setTitle(R.string.localMusic);
        toolBar.setNavigationIcon(R.drawable.ic_back);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        musicInfos = ScanMusicUtils.getLocalMusicInfoByPreference();
        mPresenter = new LocalMusicPresenter(this);
    }


    private void showScanAnimation() {
        tvScanningMusicName.animate().translationY(0).setDuration(0).setInterpolator(new AccelerateInterpolator()).start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_music_local_scan, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
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
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                mPresenter.getLocalMusicInfo(getContext());
                showScanAnimation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
