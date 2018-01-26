package com.yzx.xiaomusic.ui.play;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.service.PlayService;
import com.yzx.xiaomusic.utils.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author yzx
 * @date 2018/1/21
 * Description 播放页面
 */

public class PlayFragment extends BaseFragment {

    private static final String TAG = "yglPlayFragment";
    private static PlayFragment playFragment;
    @BindView(R.id.iv_play_bg)
    ImageView ivPlayBg;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.iv_voice_level)
    ImageView ivVoiceLevel;
    @BindView(R.id.seekBar_voice)
    AppCompatSeekBar seekBarVoice;
    @BindView(R.id.iv_play_card)
    ImageView ivPlayCard;
    @BindView(R.id.iv_play_ear)
    ImageView ivPlayEar;
    @BindView(R.id.tv_music_time_play)
    TextView tvMusicTimePlay;
    @BindView(R.id.seekBar_music_seek)
    AppCompatSeekBar seekBarMusicSeek;
    @BindView(R.id.tv_music_time_left)
    TextView tvMusicTimeLeft;
    @BindView(R.id.iv_play_mode)
    ImageView ivPlayMode;
    @BindView(R.id.iv_play_previous)
    ImageView ivPlayPrevious;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.iv_play_next)
    ImageView ivPlayNext;
    @BindView(R.id.iv_play_list)
    ImageView ivPlayList;
    @BindView(R.id.layout_play_lyrics)
    RelativeLayout layoutPlayLyrics;
    @BindView(R.id.layout_play_card)
    RelativeLayout layoutPlayCard;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_subtitle)
    TextView tvSubtitle;

    @SuppressLint("ValidFragment")
    private PlayFragment() {
    }

    public static PlayFragment getInstance() {
        if (playFragment == null) {
            playFragment = new PlayFragment();
        }
        return playFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_paly;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setToolBar(toolBar,null);
    }

    @Override
    public void onResume() {
        super.onResume();
        PlayService playService = getPlayService();
        if (PlayService.STATE_PLAYING == playService.getState()){
            ivPlay.setImageResource(R.drawable.ic_music_play_play);
        }else {
            ivPlay.setImageResource(R.drawable.ic_music_play_pause);
        }
        GlideUtils.loadImg(context,playService.getPoster(),-1 ,GlideUtils.TRANSFORM_BLUR,ivPlayBg);
        seekBarMusicSeek.setMax((int) playService.getMusicTotalTime());
        setToolBar(toolBar,playService.getMusicName(),playService.getArtist());
    }

    @OnClick({R.id.layout_play_lyrics, R.id.layout_play_card, R.id.iv_play_mode, R.id.iv_play_previous, R.id.iv_play, R.id.iv_play_next, R.id.iv_play_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_play_lyrics:
                layoutPlayCard.setVisibility(View.VISIBLE);
                layoutPlayLyrics.setVisibility(View.INVISIBLE);
                break;
            case R.id.layout_play_card:
                layoutPlayCard.setVisibility(View.INVISIBLE);
                layoutPlayLyrics.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_play_mode:
                break;
            case R.id.iv_play_previous:
                getPlayService().previous();
                break;
            case R.id.iv_play:
                if (PlayService.STATE_PLAYING == getPlayService().getState()){
                    ivPlay.setImageResource(R.drawable.ic_music_play_pause);
                }else {
                    ivPlay.setImageResource(R.drawable.ic_music_play_play);
                }
                getPlayService().playMusic();
                break;
            case R.id.iv_play_next:
                getPlayService().next();
                break;
            case R.id.iv_play_list:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }
}
