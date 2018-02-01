package com.yzx.xiaomusic.ui.mv;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.common.VideoCallBack;
import com.yzx.xiaomusic.entities.MvData;
import com.yzx.xiaomusic.utils.ToastUtils;

import butterknife.BindView;

import static com.yzx.xiaomusic.ui.main.cloud.music.songsheetdetails.SongSheetDetailsFragment.KEY_MV_ID;

/**
 *
 * @author yzx
 * @date 2018/1/21
 * Description mv页面
 */
public class MvFragment extends BaseFragment implements MvContract.View{

    private static final String TAG = "yglMvFragment";
    @BindView(R.id.videoPlayer)
    StandardGSYVideoPlayer videoPlayer;
    private static MvFragment mvFragment;
    private MvPresenter mvPresenter;

    @SuppressLint("ValidFragment")
    private MvFragment() {
    }

    public static MvFragment getInstance(){
        if (mvFragment == null){
            mvFragment = new MvFragment();
        }
        return mvFragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mv;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        mvPresenter = new MvPresenter(this);
        Bundle arguments = getArguments();
        String mvId = arguments.getString(KEY_MV_ID);
        mvPresenter.getMvAddress(mvId);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    public void setData(MvData data) {
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(null)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setUrl(data.getData().getBrs().get_$720())
                .setCacheWithPlay(false)
                .setVideoTitle(data.getData().getName())
                .setStandardVideoAllCallBack(new VideoCallBack() {

                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        ToastUtils.showToast("锁定");
                    }
                })
                .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                    @Override
                    public void onProgress(int progress, int secProgress, int currentPosition, int duration) {

                    }
                })
                .build(videoPlayer);

        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("全屏");
//                //直接横屏
//                orientationUtils.resolveByClick();
//
//                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
//                detailPlayer.startWindowFullscreen(ScrollingActivity.this, true, true);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //释放当前视频资源
        GSYVideoPlayer.releaseAllVideos();
    }
}
