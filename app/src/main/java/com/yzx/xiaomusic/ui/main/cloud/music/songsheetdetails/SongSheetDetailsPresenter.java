package com.yzx.xiaomusic.ui.main.cloud.music.songsheetdetails;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.SongSheetDetials;

/**
 * Created by yzx on 2018/1/19.
 * Description
 */

public class SongSheetDetailsPresenter implements SongSheetDetailsContract.Presenter {

    private final SongSheetDetailsModel mModel;
    private final SongSheetDetailsFragment mFragment;

    public SongSheetDetailsPresenter(SongSheetDetailsFragment fragment) {
        mFragment = fragment;
        mModel = new SongSheetDetailsModel();
    }

    @Override
    public void getSongSheetDetails(String id) {
        mFragment.stateView.showLoading();
        mModel.getSongSheetDetails(mFragment,id, new MvpObserver<SongSheetDetials>() {
            @Override
            protected void onSuccess(SongSheetDetials songSheetDetials) {
                mFragment.setDatas(songSheetDetials);
                mFragment.stateView.showContent();
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);
                mFragment.stateView.showRetry();
            }
        });
    }

}
