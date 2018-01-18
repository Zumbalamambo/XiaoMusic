package com.yzx.xiaomusic.ui.main.cloud.music.songsheet;

import android.content.Intent;
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
import com.yzx.xiaomusic.common.BaseActivity;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.SongSheetDetials;
import com.yzx.xiaomusic.network.AppHttpClient;
import com.yzx.xiaomusic.network.api.MuiscApi;
import com.yzx.xiaomusic.ui.adapter.ChildMusicAdapter;
import com.yzx.xiaomusic.ui.adapter.CloudMusicAdapter;
import com.yzx.xiaomusic.utils.GlideUtils;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yzx
 * @date 2018/1/17
 * Description  歌单详情
 */

public class SongSheetActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {
    private static final String TAG = "yglSongSheetActivity";
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

    private String songSheetId;
    private SongSheetDetials songSheetDetials;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_song_sheet;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        songSheetId = bundle.getString(ChildMusicAdapter.KEY_SONG_SHEET_ID);
        Log.i(TAG, "initView: " + songSheetId);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(toolBar);
        showBackArrow();
        showActionBarTitle(R.string.songSheet);
        collapasingToolBar.setTitleEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        final CloudMusicAdapter adapter = new CloudMusicAdapter();
        recyclerView.setAdapter(adapter);
        appBarLayout.addOnOffsetChangedListener(this);
        showLoading();
        AppHttpClient
                .getInstance()
                .getService(MuiscApi.class)
                .getSongSheetDetails(songSheetId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvpObserver<SongSheetDetials>() {
                    @Override
                    protected void onSuccess(SongSheetDetials songSheetDetials) {
                        hideLoading();
                        SongSheetActivity.this.songSheetDetials = songSheetDetials;
                        Log.i(TAG, "onSuccess: " + songSheetDetials.getResult().getCoverImgUrl());

                        GlideUtils.loadImg(context, songSheetDetials.getResult().getCoverImgUrl(), -1, GlideUtils.TRANSFORM_BLUR, ivSongSheetBg);
                        GlideUtils.loadImg(context, songSheetDetials.getResult().getCoverImgUrl(), -1, ivLittleBg);
//                        tvTitle.setText(R.string.songSheet);
                        showActionBarTitle(R.string.songSheet);
                        tvTitle.setText(songSheetDetials.getResult().getName());
                        GlideUtils.loadImg(context, songSheetDetials.getResult().getCreator().getAvatarUrl(), GlideUtils.TYPE_HEAD,GlideUtils.TRANSFORM_CIRCLE, ivHeadAuthor);
                        tvNameAuthor.setText(songSheetDetials.getResult().getCreator().getNickname());
                        adapter.setDatas(songSheetDetials.getResult().getTracks());
                    }
                });


    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        int totalScrollRange = appBarLayout.getTotalScrollRange();
        float alpha = 1+(float) verticalOffset / (float) totalScrollRange;
        Log.i(TAG, "onOffsetChanged: "+alpha);
        layoutSongSheetHead.animate().alpha(alpha).setInterpolator(new LinearInterpolator()).setDuration(0).start();
    }
}
