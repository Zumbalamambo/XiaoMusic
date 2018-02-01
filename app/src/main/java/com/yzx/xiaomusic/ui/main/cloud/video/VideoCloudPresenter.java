package com.yzx.xiaomusic.ui.main.cloud.video;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.MvData;
import com.yzx.xiaomusic.entities.VideoList;
import com.yzx.xiaomusic.utils.ToastUtils;

/**
 * Created by yzx on 2018/1/30.
 * Description
 */

public class VideoCloudPresenter implements VideoCloudContract.Presenter {

    private final VideoCloudFragment fragment;
    private final VideoCloudModel model;

    public VideoCloudPresenter(VideoCloudFragment fragment) {
        this.fragment = fragment;
        model = new VideoCloudModel();
    }

    @Override
    public void getVideoList(int offset, final boolean isRefresh) {
        model.getVideoList(fragment,offset, new MvpObserver<VideoList>() {
            @Override
            protected void onSuccess(VideoList videoList) {
                if (isRefresh){
                    fragment.onRefreshSuccess(videoList);
                }else {
                    fragment.onLoadMoreSuccess(videoList);
                }
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);
                if (isRefresh){
                    fragment.onRefreshFail(errorMsg);
                }else {
                    fragment.onLoadMoreFail(errorMsg);
                }
            }
        });
    }

    @Override
    public void getMv(String id, final int position) {

        model.getMv(fragment, id, new MvpObserver<MvData>() {
            @Override
            protected void onSuccess(MvData mvData) {
//                fragment.playVideo(mvData,position);
                fragment.adapter.playVideo(mvData);
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);
                fragment.showToast(errorMsg, ToastUtils.TYPE_FAIL);
            }
        });
    }
}
