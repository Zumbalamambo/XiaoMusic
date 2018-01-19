package com.yzx.xiaomusic.ui.main.cloud.music.songsheet;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.SongSheetDetials;

/**
 * Created by yzx on 2018/1/19.
 * Description
 */

public class SongSheetPresenter implements SongSheetContract.Presenter {

    private final SongSheetActivity mActivity;
    private final SongSheetModel mModel;

    public SongSheetPresenter(SongSheetActivity activity) {
        mActivity = activity;
        mModel = new SongSheetModel();
    }

    @Override
    public void getSongSheetDetails(String id) {
        mModel.getSongSheetDetails(id, new MvpObserver<SongSheetDetials>() {
            @Override
            protected void onSuccess(SongSheetDetials songSheetDetials) {
                mActivity.setDatas(songSheetDetials);
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);

            }
        });
    }
}
