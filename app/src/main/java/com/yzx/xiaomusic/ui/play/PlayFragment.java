package com.yzx.xiaomusic.ui.play;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.common.Constants;
import com.yzx.xiaomusic.entities.MusicMessage;
import com.yzx.xiaomusic.entities.PlayEvent;
import com.yzx.xiaomusic.entities.ProgressInfo;
import com.yzx.xiaomusic.service.PlayService;
import com.yzx.xiaomusic.ui.artistcenter.ArtistCenterFragment;
import com.yzx.xiaomusic.ui.dialog.MusicMenuDialog;
import com.yzx.xiaomusic.utils.GlideUtils;
import com.yzx.xiaomusic.utils.MusicDataUtils;
import com.yzx.xiaomusic.utils.TimeUtils;
import com.yzx.xiaomusic.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yzx.xiaomusic.service.PlayService.STATE_PLAYING;

/**
 *
 * @author yzx
 * @date 2018/1/21
 * Description 播放页面
 */

public class PlayFragment extends BaseFragment {

    private static final String TAG = "yglPlayFragment";
//    private static PlayFragment playFragment;
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
//        if (playFragment == null) {
//            playFragment = new PlayFragment();
//        }
        return new PlayFragment();
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
        Object musicInfo = playService.getMusicInfo();
        ivPlay.setImageResource(getPlayService().getState()==STATE_PLAYING? R.drawable.ic_music_play_play:R.drawable.ic_music_play_pause);
        GlideUtils.loadImg(context,MusicDataUtils.getMusicPoster(musicInfo),-1 ,GlideUtils.TRANSFORM_BLUR,ivPlayBg);
        seekBarMusicSeek.setMax((int) MusicDataUtils.getMusicDuration(musicInfo));
        setToolBar(toolBar,MusicDataUtils.getMusicName(musicInfo),MusicDataUtils.getMusicArtist(musicInfo));
        tvMusicTimePlay.setText(TimeUtils.parseTime(playService.getProgress()));
        tvMusicTimeLeft.setText(TimeUtils.parseTime(MusicDataUtils.getMusicDuration(musicInfo)-playService.getProgress()));
    }

    @OnClick({R.id.tv_subtitle,R.id.layout_play_lyrics, R.id.layout_play_card, R.id.iv_play_mode, R.id.iv_play_previous,
            R.id.iv_play, R.id.iv_play_next, R.id.iv_play_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_subtitle:
                String musicArtistId = MusicDataUtils.getMusicArtistId(getPlayService().getMusicInfo());
                if (TextUtils.isEmpty(musicArtistId)){
                    ToastUtils.showToast(R.string.no_artist_id,ToastUtils.TYPE_NOTICE);
                }else {
                    ArtistCenterFragment artistCenterFragment = ArtistCenterFragment.getInstance();
                    Bundle args=new Bundle();
                    args.putString(Constants.KEY_ARTIST_ID, musicArtistId);
                    artistCenterFragment.setArguments(args);
                    start(artistCenterFragment);
                }
                break;
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
                getPlayService().playMusic();
                break;
            case R.id.iv_play_next:
                getPlayService().next();
                break;
            case R.id.iv_play_list:
                MusicMenuDialog dialog=new MusicMenuDialog();
                dialog.show(getActivity().getSupportFragmentManager(),"musicMuenu");
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlayEvent event) {

        switch (event.type){
            case PlayEvent.TYPE_CHANGE:
                MusicMessage musicMessage = (MusicMessage) event.getData();
                tvTitle.setText(musicMessage.getName());
                tvSubtitle.setText(musicMessage.getArtist());
                GlideUtils.loadImg(context,musicMessage.getPoster(),-1,GlideUtils.TRANSFORM_BLUR,ivPlayBg);
                break;
            case PlayEvent.TYPE_PLAY:
                ivPlay.setImageResource(R.drawable.ic_music_play_play);
                break;
            case PlayEvent.TYPE_PAUSE:
                ivPlay.setImageResource(R.drawable.ic_music_play_pause);
                break;
            case PlayEvent.TYPE_PROCESS:

                ProgressInfo progressInfo = (ProgressInfo) event.getData();
                tvMusicTimePlay.setText(TimeUtils.parseTime(progressInfo.getProcess()));
                tvMusicTimeLeft.setText(TimeUtils.parseTime(progressInfo.getDuration()-progressInfo.getProcess()));
                seekBarMusicSeek.setMax((int) progressInfo.getDuration());
                seekBarMusicSeek.setProgress(progressInfo.getProcess());
                break;
            default:
                break;
        }

    };
}
