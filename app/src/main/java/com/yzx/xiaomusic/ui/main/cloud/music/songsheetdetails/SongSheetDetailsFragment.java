package com.yzx.xiaomusic.ui.main.cloud.music.songsheetdetails;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.entities.SongSheetDetials;
import com.yzx.xiaomusic.service.PlayEvent;
import com.yzx.xiaomusic.service.PlayService;
import com.yzx.xiaomusic.ui.adapter.ChildCloudMusicAdapter;
import com.yzx.xiaomusic.ui.adapter.CommonMusicAdapter;
import com.yzx.xiaomusic.ui.mv.MvFragment;
import com.yzx.xiaomusic.ui.play.PlayFragment;
import com.yzx.xiaomusic.utils.GlideUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yzx.xiaomusic.service.PlayService.STATE_PLAYING;
import static com.yzx.xiaomusic.service.PlayService.TYPE_NET;
import static com.yzx.xiaomusic.ui.adapter.CommonMusicAdapter.DATA_TYPE_SONG_SHEET_MUSIC;

/**
 * @author yzx
 * @date 2018/1/17
 * Description  歌单详情
 */

public class SongSheetDetailsFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener, SongSheetDetailsContract.View, OnItemClickLsitener {
    private static final String TAG = "yglSongSheetActivity";
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
    @BindView(R.id.iv_music_play)
    ImageView ivMusicPlay;

    private CommonMusicAdapter adapter;
    private SongSheetDetailsPresenter mPresenter;
    private String songSheetTitle;
    private String subTitle;

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
        return R.layout.activity_song_sheet;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle arguments = getArguments();
        SongSheet.PlaylistsBean playlistsBean = (SongSheet.PlaylistsBean) arguments.getSerializable(ChildCloudMusicAdapter.KEY_SONG_SHEET);
        GlideUtils.loadImg(context, playlistsBean.getCoverImgUrl(), -1, GlideUtils.TRANSFORM_BLUR, ivSongSheetBg);
        GlideUtils.loadImg(context, playlistsBean.getCoverImgUrl(), -1, ivLittleBg);
        songSheetTitle = playlistsBean.getName();
        subTitle = playlistsBean.getDescription();
        tvSongSheetTitle.setText(playlistsBean.getName());
        mPresenter = new SongSheetDetailsPresenter(this);
        getSongSheetDetails(String.valueOf(playlistsBean.getId()));
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
        adapter.setDatas(DATA_TYPE_SONG_SHEET_MUSIC, detials.getResult().getTracks());
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
     * @param type     类型
     */
    @Override
    public void onItemClickListener(View itemView, int position, Object data, int type) {

        switch (itemView.getId()) {
            case R.id.iv_mv:
                start(MvFragment.getInstance());
                break;
            default:
                SongSheetDetials.ResultBean.TracksBean tracksBean = (SongSheetDetials.ResultBean.TracksBean) data;
                PlayService playService = getPlayService();
                tvMusicName.setText(playService.getMusicName());
                tvMusicArtist.setText(playService.getArtist());
                ivMusicPlay.setImageResource(playService.getState()==STATE_PLAYING?R.drawable.ic_bottom_play:R.drawable.ic_bottom_pause);
                if (TYPE_NET != getPlayService().getMusicType() || !String.valueOf(tracksBean.getId()).equals(getPlayService().getMusicId())) {
                    getPlayService().setState(PlayService.STATE_IDLE);
                    playService.setMusicType(PlayService.TYPE_NET);
                    playService.setMusicId(String.valueOf(tracksBean.getId()));
                }
                playService.playMusic();
                break;
        }
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
