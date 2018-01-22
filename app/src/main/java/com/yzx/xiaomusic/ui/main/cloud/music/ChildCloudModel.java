package com.yzx.xiaomusic.ui.main.cloud.music;

import com.yzx.xiaomusic.common.BaseActivity;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.Banner;
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.network.AppHttpClient;
import com.yzx.xiaomusic.network.api.MuiscApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yzx on 2018/1/19.
 * Description
 */

public class ChildCloudModel implements ChildCloudContract.Model<BaseActivity> {


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

    @Override
    public void getBanner(MvpObserver<Banner> observer) {
        AppHttpClient
                .getInstance()
                .getService(MuiscApi.class)
                .getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
