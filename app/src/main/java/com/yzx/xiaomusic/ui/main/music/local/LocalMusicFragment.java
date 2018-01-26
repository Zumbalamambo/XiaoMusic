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
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.common.OnItemClickLsitener;
import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.service.PlayEvent;
import com.yzx.xiaomusic.service.PlayService;
import com.yzx.xiaomusic.ui.adapter.CommonMusicAdapter;
import com.yzx.xiaomusic.ui.play.PlayFragment;
import com.yzx.xiaomusic.utils.GlideUtils;
import com.yzx.xiaomusic.utils.ScanMusicUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.yzx.xiaomusic.service.PlayService.STATE_PLAYING;
import static com.yzx.xiaomusic.service.PlayService.TYPE_LOCAL;

/**
 * @author yzx
 * @date 2018/1/19
 * Description  本地音乐页面
 */

public class LocalMusicFragment extends BaseFragment implements OnItemClickLsitener{
    private static final String TAG = "yglLocalMusicFragment";
    private static LocalMusicFragment localMusicFragment;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    public static final String MUSIC_INFO = "musicInfo";
    @BindView(R.id.tv_scanning_music_name)
    TextView tvScanningMusicName;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.layout_music_control)
    LinearLayout layoutMusicControl;
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
    Unbinder unbinder;
    private List<MusicInfo> musicInfos;
    private LocalMusicPresenter mPresenter;
    public CommonMusicAdapter adapter;

    @SuppressLint("ValidFragment")
    private LocalMusicFragment() {
    }

    public static LocalMusicFragment getInstance() {
        if (localMusicFragment == null) {
            localMusicFragment = new LocalMusicFragment();
        }
        return localMusicFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music_local;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        musicInfos = ScanMusicUtils.getLocalMusicInfoByPreference();
        mPresenter = new LocalMusicPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setToolBar(toolBar, R.string.localMusic);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommonMusicAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        if (musicInfos == null) {
            mPresenter.getLocalMusicInfo(getContext());
            showScanAnimation();
        }
        adapter.setDatas(CommonMusicAdapter.DATA_TYPE_LOCAL_MUSIC, musicInfos);
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

    @Override
    public void onItemClickListener(View itemView, int position, Object data, int type) {
        MusicInfo musicInfo = (MusicInfo) data;
        PlayService playService = getPlayService();

        if (TYPE_LOCAL != getPlayService().getMusicType() || !musicInfo.getMd5().equals(getPlayService().getMd5())) {
            getPlayService().setState(PlayService.STATE_IDLE);
            getPlayService().setMusicType(PlayService.TYPE_LOCAL);
            getPlayService().setMd5(musicInfo.md5);
        }
        playService.playMusic();
    }

    @OnClick({R.id.iv_music_play, R.id.iv_music_menu,R.id.layout_music_control})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
