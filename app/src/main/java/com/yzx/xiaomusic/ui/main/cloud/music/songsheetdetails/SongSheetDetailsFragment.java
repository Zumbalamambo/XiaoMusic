package com.yzx.xiaomusic.ui.main.cloud.music.songsheetdetails;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.common.OnItemClickLsitener;
import com.yzx.xiaomusic.entities.MusicMessage;
import com.yzx.xiaomusic.entities.PlayEvent;
import com.yzx.xiaomusic.entities.ProgressInfo;
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.entities.SongSheetDetials;
import com.yzx.xiaomusic.service.PlayService;
import com.yzx.xiaomusic.ui.adapter.ChildCloudMusicAdapter;
import com.yzx.xiaomusic.ui.adapter.CommonMusicAdapter;
import com.yzx.xiaomusic.ui.dialog.MusicMenuDialog;
import com.yzx.xiaomusic.ui.mv.MvFragment;
import com.yzx.xiaomusic.ui.play.PlayFragment;
import com.yzx.xiaomusic.utils.GlideUtils;
import com.yzx.xiaomusic.utils.MusicDataUtils;
import com.yzx.xiaomusic.widget.CircleProgress;
import com.yzx.xiaomusic.widget.StateView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author yzx
 * @date 2018/1/17
 * Description  歌单详情
 */

public class SongSheetDetailsFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener, SongSheetDetailsContract.View, OnItemClickLsitener, StateView.OnRetryClickListener {
    private static final String TAG = "yglSongSheetActivity";
    public static final String KEY_MV_ID = "mvId";
    private static SongSheetDetailsFragment songSheetFragment;
    @BindView(R.id.iv_songSheetBg)
    ImageView ivSongSheetBg;
    @BindView(R.id.iv_littleBg)
    ImageView ivLittleBg;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_song_sheet_title)
    TextView tvSongSheetTitle;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_head_author)
    ImageView ivHeadAuthor;
    @BindView(R.id.tv_name_author)
    TextView tvNameAuthor;
    @BindView(R.id.collapasingToolBar)
    CollapsingToolbarLayout collapasingToolBar;
    @BindView(R.id.layout_songSheetHead)
    RelativeLayout layoutSongSheetHead;
    @BindView(R.id.tv_collect_num)
    TextView tvCollectionNum;
    @BindView(R.id.tv_evaluate_num)
    TextView tvEvaluteNum;
    @BindView(R.id.tv_share_num)
    TextView tvShareNum;
    @BindView(R.id.layout_music_control)
    LinearLayout layoutMusicControl;
    @BindView(R.id.iv_music_poster)
    ImageView ivMusicPoster;
    @BindView(R.id.tv_music_name)
    TextView tvMusicName;
    @BindView(R.id.tv_music_artist)
    TextView tvMusicArtist;
    @BindView(R.id.tv_download_num)
    TextView tvDownloadNum;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_subtitle)
    TextView tvSubtitle;
    @BindView(R.id.circleProgress)
    CircleProgress circleProgress;
    @BindView(R.id.layout_state_container)
    RelativeLayout layoutStateContainer;
    private CommonMusicAdapter adapter;
    private SongSheetDetailsPresenter mPresenter;
    private String songSheetTitle;
    private String subTitle;
    public StateView stateView;
    private String songSheetId;

    @SuppressLint("ValidFragment")
    private SongSheetDetailsFragment() {
    }

    public static SongSheetDetailsFragment getInstance() {
        if (songSheetFragment == null) {
            songSheetFragment = new SongSheetDetailsFragment();
        }
        return songSheetFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_song_sheet;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        stateView = StateView.inject(layoutStateContainer);
        stateView.setOnRetryClickListener(this);
        Bundle arguments = getArguments();
        SongSheet.PlaylistsBean playlistsBean = (SongSheet.PlaylistsBean) arguments.getSerializable(ChildCloudMusicAdapter.KEY_SONG_SHEET);
        GlideUtils.loadImg(context, playlistsBean.getCoverImgUrl(), -1, GlideUtils.TRANSFORM_BLUR, ivSongSheetBg);
        GlideUtils.loadImg(context, playlistsBean.getCoverImgUrl(), -1, ivLittleBg);
        songSheetTitle = playlistsBean.getName();
        subTitle = playlistsBean.getDescription();
        tvSongSheetTitle.setText(playlistsBean.getName());
        mPresenter = new SongSheetDetailsPresenter(this);
        songSheetId = String.valueOf(playlistsBean.getId());
        getSongSheetDetails(songSheetId);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setToolBar(toolBar, R.string.songSheet);
        collapasingToolBar.setTitleEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommonMusicAdapter();
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        appBarLayout.addOnOffsetChangedListener(this);

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        int totalScrollRange = appBarLayout.getTotalScrollRange();
        float alpha = 1 + (float) verticalOffset / (float) totalScrollRange;
        layoutSongSheetHead.animate().alpha(alpha).setInterpolator(new LinearInterpolator()).setDuration(0).start();
    }

    @Override
    public void getSongSheetDetails(String id) {
        Log.i(TAG, "getSongSheetDetails: " + id);
        mPresenter.getSongSheetDetails(id);
    }

    @Override
    public void setDatas(SongSheetDetials detials) {

        GlideUtils.loadImg(context, detials.getResult().getCreator().getAvatarUrl(), GlideUtils.TYPE_HEAD, GlideUtils.TRANSFORM_CIRCLE, ivHeadAuthor);
        tvNameAuthor.setText(detials.getResult().getCreator().getNickname());
        adapter.setDatas(detials);
        tvCollectionNum.setText(String.valueOf(detials.getResult().getSubscribedCount()));
        tvEvaluteNum.setText(String.valueOf(detials.getResult().getCommentCount()));
        tvShareNum.setText(String.valueOf(detials.getResult().getShareCount()));
    }

    /**
     * 歌曲列表点击
     *
     * @param itemView
     * @param position
     * @param data     需要的数据
     */
    @Override
    public void onItemClickListener(View itemView, int position, Object data) {
        SongSheetDetials.ResultBean.TracksBean tracksBean = (SongSheetDetials.ResultBean.TracksBean) data;
        switch (itemView.getId()) {
            case R.id.iv_mv:
                MvFragment mvFragment = MvFragment.getInstance();
                if (!TextUtils.isEmpty(String.valueOf(tracksBean.getMvid()))){
                    Bundle args =new Bundle();
                    args.putString(KEY_MV_ID, String.valueOf(tracksBean.getMvid()));
                    mvFragment.setArguments(args);
                    start(mvFragment);
                }
                break;
            default:
                PlayService playService = getPlayService();
                if (MusicDataUtils.TYPE_SONG_SHEET !=MusicDataUtils.getMusicType(playService.getMusicInfo())||playService.getPlayListPosition()!=position){
                    getPlayService().setState(PlayService.STATE_IDLE);
                    getPlayService().setPlayListPosition(position);
                }
                playService.playMusic();
                break;
        }
    }



    @OnClick({R.id.circleProgress, R.id.iv_music_menu,R.id.layout_music_control})
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
                start(PlayFragment.getInstance());
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
        setUpBottomPlayControl(tvMusicName,tvMusicArtist,circleProgress,ivMusicPoster);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlayEvent event) {
        switch (event.type){
            case PlayEvent.TYPE_CHANGE:
                MusicMessage musicMessage = (MusicMessage) event.getData();
                tvMusicName.setText(musicMessage.getName());
                tvMusicArtist.setText(musicMessage.getArtist());
                GlideUtils.loadImg(context,musicMessage.getPoster(),GlideUtils.TYPE_DEFAULT,ivMusicPoster);
                break;
            case PlayEvent.TYPE_PLAY:
                circleProgress.setState(CircleProgress.STATE_PLAY);
                break;
            case PlayEvent.TYPE_PAUSE:
                circleProgress.setState(CircleProgress.STATE_PAUSE);
                break;
            case PlayEvent.TYPE_PROCESS:

                ProgressInfo progressInfo = (ProgressInfo) event.getData();
//                Log.i(TAG, progressInfo.getProcess()+"onMessageEvent: "+progressInfo.getDuration());
                circleProgress.setMax((int) progressInfo.getDuration());
                circleProgress.setProgress(progressInfo.getProcess());
                break;
            default:
                break;
        }

    };

    @Override
    public void onRetryClick() {
        getSongSheetDetails(songSheetId);
    }
}
