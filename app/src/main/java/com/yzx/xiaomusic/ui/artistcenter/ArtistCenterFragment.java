package com.yzx.xiaomusic.ui.artistcenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.common.Constants;
import com.yzx.xiaomusic.common.OnItemClickLsitener;
import com.yzx.xiaomusic.entities.ArtistCenterInfo;
import com.yzx.xiaomusic.entities.MusicMessage;
import com.yzx.xiaomusic.entities.PlayEvent;
import com.yzx.xiaomusic.entities.ProgressInfo;
import com.yzx.xiaomusic.service.PlayService;
import com.yzx.xiaomusic.ui.adapter.CommonMusicAdapter;
import com.yzx.xiaomusic.ui.dialog.MusicMenuDialog;
import com.yzx.xiaomusic.ui.mv.MvFragment;
import com.yzx.xiaomusic.ui.play.PlayFragment;
import com.yzx.xiaomusic.utils.GlideUtils;
import com.yzx.xiaomusic.utils.MusicDataUtils;
import com.yzx.xiaomusic.utils.ToastUtils;
import com.yzx.xiaomusic.widget.CircleProgress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

import static com.yzx.xiaomusic.ui.main.cloud.music.songsheetdetails.SongSheetDetailsFragment.KEY_MV_ID;

/**
 * Created by yzx on 2018/2/2.
 * Description
 */

public class ArtistCenterFragment extends BaseFragment implements OnItemClickLsitener {

    private static final String TAG = "yglArtistCenterFragment";
    private static ArtistCenterFragment fragment;
    @BindView(R.id.iv_artist_poster)
    public ImageView ivArtistPoster;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_music_poster)
    ImageView ivMusicPoster;
    @BindView(R.id.tv_music_name)
    TextView tvMusicName;
    @BindView(R.id.tv_music_artist)
    TextView tvMusicArtist;
    @BindView(R.id.circleProgress)
    CircleProgress circleProgress;
    private String artistId;
    private ArtistCenterPresenter mPresenter;
    public CommonMusicAdapter adapter;

    @SuppressLint("ValidFragment")
    private ArtistCenterFragment() {
    }

    public static ArtistCenterFragment getInstance() {
        if (fragment == null) {
            fragment = new ArtistCenterFragment();
        }
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_artist_center;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mPresenter = new ArtistCenterPresenter(this);
        Bundle arguments = getArguments();
        artistId = arguments.getString(Constants.KEY_ARTIST_ID);
        if (TextUtils.isEmpty(artistId)) {
            ToastUtils.showToast(R.string.error_get_data, ToastUtils.TYPE_FAIL);
            return;
        }
        mPresenter.getArtistCenterInfo(artistId);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CommonMusicAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClickListener(View itemView, int position, Object data) {
        ArtistCenterInfo.HotSongsBean hotSongsBean = (ArtistCenterInfo.HotSongsBean) data;
        switch (itemView.getId()) {
            case R.id.iv_mv:
                MvFragment mvFragment = MvFragment.getInstance();
                if (!TextUtils.isEmpty(String.valueOf(hotSongsBean.getMv()))) {
                    Bundle args = new Bundle();
                    args.putString(KEY_MV_ID, String.valueOf(hotSongsBean.getMv()));
                    mvFragment.setArguments(args);
                    start(mvFragment);
                }
                break;
            default:
                PlayService playService = getPlayService();
                if (MusicDataUtils.TYPE_ARTIST_CENTER != MusicDataUtils.getMusicType(playService.getMusicInfo()) || playService.getPlayListPosition() != position) {
                    getPlayService().setState(PlayService.STATE_IDLE);
                    getPlayService().setPlayListPosition(position);
                }
                Log.i(TAG, "onItemClickListener: "+position);
                playService.playMusic();
                break;
        }
    }
    @OnClick({R.id.circleProgress,R.id.iv_music_menu, R.id.layout_music_control})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.circleProgress:
                getPlayService().playMusic();
                break;
            case R.id.iv_music_menu:
                MusicMenuDialog dialog=new MusicMenuDialog();
                dialog.show(getActivity().getSupportFragmentManager(),"musicMuenu");
                break;
            case R.id.layout_music_control:
                start(PlayFragment.getInstance(), SupportFragment.SINGLETASK);
                break;
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
        setUpBottomPlayControl(tvMusicName,tvMusicArtist, circleProgress, ivMusicPoster);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlayEvent event) {
        switch (event.type){
            case PlayEvent.TYPE_CHANGE:
                MusicMessage musicMessage = (MusicMessage) event.getData();
                tvMusicName.setText(musicMessage.getName());
                tvMusicArtist.setText(musicMessage.getArtist());
                GlideUtils.loadImg(context,musicMessage.getPoster(),GlideUtils.TYPE_ARTIST_CENTER,ivMusicPoster);
                break;
            case PlayEvent.TYPE_PLAY:
                circleProgress.setState(CircleProgress.STATE_PLAY);
                break;
            case PlayEvent.TYPE_PAUSE:
                circleProgress.setState(CircleProgress.STATE_PAUSE);
                break;
            case PlayEvent.TYPE_PROCESS:
                ProgressInfo progressInfo = (ProgressInfo) event.getData();
                circleProgress.setMax((int) progressInfo.getDuration());
                circleProgress.setProgress(progressInfo.getProcess());
                break;
            default:
                break;
        }

    };
}
