package com.yzx.xiaomusic.ui.main.cloud.music.songsheetlist;

import com.yzx.xiaomusic.common.BaseActivity;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.network.AppHttpClient;
import com.yzx.xiaomusic.network.api.MuiscApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yzx on 2018/1/19.
 * Description
 */

public class SongSheetListModel implements SongSheetListContract.Model<SongSheet,BaseActivity> {


    @Override
    public void getSongSheetDetails(String cat, String order, int offset, int limit, boolean total, MvpObserver<SongSheet> observer) {
        AppHttpClient
                .getInstance()
                .getService(MuiscApi.class)
                .getSongSheet(cat,order,offset,total,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}