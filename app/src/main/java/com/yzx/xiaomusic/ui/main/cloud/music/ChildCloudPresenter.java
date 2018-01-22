package com.yzx.xiaomusic.ui.main.cloud.music;

import android.util.Log;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.Banner;
import com.yzx.xiaomusic.entities.SongSheet;

/**
 * Created by yzx on 2018/1/19.
 * Description
 */

public class ChildCloudPresenter implements ChildCloudContract.Presenter {

    private final ChildCloudModel mModel;
    private final ChildCloudFragment mFragment;

    public ChildCloudPresenter(ChildCloudFragment fragment) {
        mFragment = fragment;
        mModel = new ChildCloudModel();
    }

    @Override
    public void getSongSheetDetails(String cat, String order, int offset, int limit, boolean total) {

        mModel.getSongSheetDetails(cat, order, offset, limit, true, new MvpObserver<SongSheet>() {
            @Override
            protected void onSuccess(SongSheet songSheet) {
                mFragment.songSheetAdapter.setSongSheetDatas(songSheet);
                Log.i(TAG, "onSuccess: 获取歌单成功");
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);
            }
        });
    }

    @Override
    public void getBanner() {
        mModel.getBanner(new MvpObserver<Banner>() {
            @Override
            protected void onSuccess(Banner banner) {
                mFragment.bannerAdapter.setBannerDatas(banner.getBanners());
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);
                Log.i(TAG, "onFail: 获取banner数据失败"+errorMsg);
            }
        });
    }

}
