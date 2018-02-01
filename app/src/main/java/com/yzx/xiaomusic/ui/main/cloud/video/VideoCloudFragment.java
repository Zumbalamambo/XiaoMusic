package com.yzx.xiaomusic.ui.main.cloud.video;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.NormalGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.common.BaseView;
import com.yzx.xiaomusic.common.OnItemClickLsitener;
import com.yzx.xiaomusic.common.RefreshView;
import com.yzx.xiaomusic.common.VideoCallBack;
import com.yzx.xiaomusic.entities.VideoList;
import com.yzx.xiaomusic.ui.adapter.VideoAdapter;
import com.yzx.xiaomusic.ui.main.MainFragment;
import com.yzx.xiaomusic.ui.mv.MvFragment;
import com.yzx.xiaomusic.utils.SmallVideoHelper;
import com.yzx.xiaomusic.utils.ToastUtils;

import butterknife.BindView;

import static com.yzx.xiaomusic.ui.main.cloud.music.songsheetdetails.SongSheetDetailsFragment.KEY_MV_ID;


/**
 * Created by yzx on 2018/1/12.
 * Description 视频页面
 */

public class VideoCloudFragment extends BaseFragment implements VideoCloudContract.View,BaseView, XRecyclerView.LoadingListener,RefreshView<VideoList>,OnItemClickLsitener {

    private static final String TAG = "yglVideoCloudFragment";
    private static VideoCloudFragment videoCloudFragment;
    @BindView(R.id.recyclerView)
    XRecyclerView recyclerView;
    @BindView(R.id.fl_littleScreenContainer)
    FrameLayout flLittleScreenContainer;
    private VideoCloudPresenter presenter;
    private int offset;
    public VideoAdapter adapter;
    private SmallVideoHelper smallVideoHelper;
    private SmallVideoHelper.GSYSmallVideoHelperBuilder smallVideoHelperBuilder;

    int lastVisibleItem;

    int firstVisibleItem;
    @SuppressLint("ValidFragment")
    private VideoCloudFragment() {
    }

    public static VideoCloudFragment getInstance() {
        if (videoCloudFragment == null) {
            videoCloudFragment = new VideoCloudFragment();
        }
        return videoCloudFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cloud_video;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        presenter = new VideoCloudPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new VideoAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadingListener(this);
        recyclerView.refresh();
        adapter.setOnItemClickListener(this);
        //小窗口
        smallVideoHelper = new SmallVideoHelper(context, new NormalGSYVideoPlayer(context));
        smallVideoHelperBuilder = new SmallVideoHelper.GSYSmallVideoHelperBuilder();
          smallVideoHelperBuilder.setHideActionBar(true)
                .setHideStatusBar(true)
                .setNeedLockFull(true)
                .setCacheWithPlay(true)
                .setShowFullAnimation(true)
                .setLockLand(true).setVideoAllCallBack(new VideoCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        Debuger.printfLog("Duration " + smallVideoHelper.getGsyVideoPlayer().getDuration() + " CurrentPosition " + smallVideoHelper.getGsyVideoPlayer().getCurrentPositionWhenPlaying());
                    }

                    @Override
                    public void onQuitSmallWidget(String url, Object... objects) {
                        super.onQuitSmallWidget(url, objects);
                        //大于0说明有播放,//对应的播放列表TAG
                        if (smallVideoHelper.getPlayPosition() >= 0 && smallVideoHelper.getPlayTAG().equals(VideoAdapter.TAG)) {
                            //当前播放的位置
                            int position = smallVideoHelper.getPlayPosition();
                            //不可视的是时候
                            if ((position < firstVisibleItem || position > lastVisibleItem)) {
                                //释放掉视频
                                smallVideoHelper.releaseVideoPlayer();
                                adapter.notifyDataSetChanged();
                            }
                        }

                    }
                });
        smallVideoHelper.setGsyVideoOptionBuilder(smallVideoHelperBuilder);

        adapter.setVideoHelper(smallVideoHelper, smallVideoHelperBuilder);

//        滑动控制
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem   = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Debuger.printfLog("firstVisibleItem " + firstVisibleItem +" lastVisibleItem " + lastVisibleItem);
                //大于0说明有播放,//对应的播放列表TAG
                if (smallVideoHelper.getPlayPosition() >= 0 && smallVideoHelper.getPlayTAG().equals(VideoAdapter.TAG)) {
                    //当前播放的位置
                    int position = smallVideoHelper.getPlayPosition();
                    //不可视的是时候
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果是小窗口就不需要处理
                        if (!smallVideoHelper.isSmall() && !smallVideoHelper.isFull()) {
                            //小窗口
                            int size = CommonUtil.dip2px(context, 150);
                            //actionbar为true才不会掉下面去
                            smallVideoHelper.showSmallVideo(new Point(size, size), true, true);
                        }
                    } else {
                        if (smallVideoHelper.isSmall()) {
                            smallVideoHelper.smallVideoToNormal();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void getVideoList(int offset,boolean isRefresh) {
        GSYVideoPlayer.releaseAllVideos();
        presenter.getVideoList(offset,isRefresh);
    }

    @Override
    public void onRefresh() {
        offset = 0;

        getVideoList(offset,true);
    }

    @Override
    public void onLoadMore() {
        offset+=10;
        getVideoList(offset,false);
    }

    @Override
    public void onRefreshSuccess(VideoList videoList) {
        adapter.setVideoData(videoList.getData());
        recyclerView.refreshComplete();
    }

    @Override
    public void onRefreshFail(String errorMsg) {
        showToast(errorMsg, ToastUtils.TYPE_FAIL);
        recyclerView.refreshComplete();
        offset=0;
    }

    @Override
    public void onLoadMoreSuccess(VideoList videoList) {
        adapter.addVideoData(videoList.getData());
        recyclerView.loadMoreComplete();
    }

    @Override
    public void onLoadMoreFail(String errorMsg) {
        showToast(errorMsg, ToastUtils.TYPE_FAIL);
        recyclerView.loadMoreComplete();
        offset--;
    }

    @Override
    public void onItemClickListener(View itemView, int position, Object data) {
        VideoList.DataBean videoData = (VideoList.DataBean) data;
        switch (itemView.getId()){
            case R.id.iv_cover:
                Log.i(TAG, "onItemClickListener: 封面");
                presenter.getMv(String.valueOf(videoData.getId()),position);
                break;
            case R.id.iv_more:
                Log.i(TAG, "onItemClickListener: 更多");
                break;
            default:
                Log.i(TAG, "onItemClickListener: itemBiew");
//                smallVideoHelper.releaseVideoPlayer();
                MainFragment parentFragment = (MainFragment) getParentFragment().getParentFragment();
                MvFragment mvFragment = MvFragment.getInstance();
                Bundle args =new Bundle();
                args.putString(KEY_MV_ID, String.valueOf(videoData.getId()));
                mvFragment.setArguments(args);
                parentFragment.start(mvFragment);
                break;
        }
    }
}
