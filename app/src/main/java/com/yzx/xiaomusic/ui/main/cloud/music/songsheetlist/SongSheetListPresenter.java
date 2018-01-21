package com.yzx.xiaomusic.ui.main.cloud.music.songsheetlist;

import android.util.Log;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.SongSheet;

/**
 * Created by yzx on 2018/1/19.
 * Description
 */

public class SongSheetListPresenter implements SongSheetListContract.Presenter {

    private final SongSheetListModel mModel;
    private final SongSheetListFragment mFragment;

    public SongSheetListPresenter(SongSheetListFragment fragment) {
        mFragment = fragment;
        mModel = new SongSheetListModel();
    }

    @Override
    public void getSongSheetDetails(String cat, String order, final int offset, int limit, boolean total) {

        mModel.getSongSheetDetails(cat, order, offset, limit, true, new MvpObserver<SongSheet>() {
            @Override
            protected void onSuccess(SongSheet songSheet) {
                mFragment.adapter.setDatas(songSheet.getPlaylists());
                mFragment.recyclerView.loadMoreComplete();
                mFragment.offset += 10;
                Log.i(TAG, "onSuccess: 获取歌单成功"+offset);
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);
                mFragment.offset--;
                mFragment.recyclerView.loadMoreComplete();
            }
        });
    }
}
