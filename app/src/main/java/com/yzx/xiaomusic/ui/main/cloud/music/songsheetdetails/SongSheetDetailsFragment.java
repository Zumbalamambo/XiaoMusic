package com.yzx.xiaomusic.ui.main.cloud.music.songsheetdetails;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.entities.SongSheetDetials;
import com.yzx.xiaomusic.ui.adapter.ChildCloudMusicAdapter;
import com.yzx.xiaomusic.ui.adapter.CloudMusicAdapter;
import com.yzx.xiaomusic.utils.GlideUtils;

import butterknife.BindView;

/**
 * @author yzx
 * @date 2018/1/17
 * Description  歌单详情
 */

public class SongSheetDetailsFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener,SongSheetDetailsContract.View {
    private static final String TAG = "yglSongSheetActivity";
    private static SongSheetDetailsFragment songSheetFragment;
    @BindView(R.id.iv_songSheetBg)
    ImageView ivSongSheetBg;
    @BindView(R.id.iv_littleBg)
    ImageView ivLittleBg;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
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

    private CloudMusicAdapter adapter;
    private SongSheetDetailsPresenter mPresenter;
    private String songSheetTitle;

    @SuppressLint("ValidFragment")
    private SongSheetDetailsFragment() {
    }

    public static SongSheetDetailsFragment getInstance(){
        if (songSheetFragment==null){
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

        tvTitle.setText(playlistsBean.getName());
        mPresenter = new SongSheetDetailsPresenter(this);
        getSongSheetDetails(String.valueOf(playlistsBean.getId()));
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setToolBar(toolBar,R.string.songSheet);
        collapasingToolBar.setTitleEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CloudMusicAdapter();
        recyclerView.setAdapter(adapter);
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        int totalScrollRange = appBarLayout.getTotalScrollRange();
        float alpha = 1+(float) verticalOffset / (float) totalScrollRange;
        layoutSongSheetHead.animate().alpha(alpha).setInterpolator(new LinearInterpolator()).setDuration(0).start();
        if (Math.abs(verticalOffset)> totalScrollRange*0.2f){
            setToolBar(toolBar,songSheetTitle);
        }else {
            setToolBar(toolBar,R.string.songSheet);
        }
    }

    @Override
    public void getSongSheetDetails(String id) {
        Log.i(TAG, "getSongSheetDetails: "+id);
        mPresenter.getSongSheetDetails(id);
    }

    @Override
    public void setDatas(SongSheetDetials detials) {

        GlideUtils.loadImg(context, detials.getResult().getCreator().getAvatarUrl(), GlideUtils.TYPE_HEAD,GlideUtils.TRANSFORM_CIRCLE, ivHeadAuthor);
        tvNameAuthor.setText(detials.getResult().getCreator().getNickname());
        adapter.setDatas(detials.getResult().getTracks());
        tvCollectionNum.setText(String.valueOf(detials.getResult().getSubscribedCount()));
        tvEvaluteNum.setText(String.valueOf(detials.getResult().getCommentCount()));
        tvShareNum.setText(String.valueOf(detials.getResult().getShareCount()));
    }
}
