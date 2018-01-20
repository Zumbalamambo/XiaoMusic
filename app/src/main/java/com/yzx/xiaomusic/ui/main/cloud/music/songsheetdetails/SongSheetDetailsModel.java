package com.yzx.xiaomusic.ui.main.cloud.music.songsheetdetails;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.SongSheetDetials;
import com.yzx.xiaomusic.network.AppHttpClient;
import com.yzx.xiaomusic.network.api.MuiscApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yzx on 2018/1/19.
 * Description
 */

public class SongSheetDetailsModel implements SongSheetDetailsContract.Model<SongSheetDetials> {

    @Override
    public void getSongSheetDetails(String id, MvpObserver<SongSheetDetials> observer) {
        AppHttpClient
                .getInstance()
                .getService(MuiscApi.class)
                .getSongSheetDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
