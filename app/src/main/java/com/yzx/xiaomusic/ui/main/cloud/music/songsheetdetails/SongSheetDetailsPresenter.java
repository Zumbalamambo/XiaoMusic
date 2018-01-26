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
        mModel.getSongSheetDetails(mFragment,id, new MvpObserver<SongSheetDetials>() {
            @Override
            protected void onSuccess(SongSheetDetials songSheetDetials) {
                mFragment.setDatas(songSheetDetials);
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);

            }
        });
    }

//    @Override
//    public void getMusicAddress(String id) {
//        mModel.getMusicAddress(id, new MvpObserver<MusicAddress>() {
//            @Override
//            protected void onSuccess(MusicAddress musicAddress) {
//                Log.i(TAG, "onSuccess: 网络音乐");
//                mFragment.getPlayService().play(musicAddress, PlayService.TYPE_NET);
//            }
//
//            @Override
//            protected void onFail(String errorMsg) {
//                super.onFail(errorMsg);
//
//            }
//        });
//    }
}
