package com.yzx.xiaomusic.ui.main.cloud.video;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.MvData;
import com.yzx.xiaomusic.entities.VideoList;
import com.yzx.xiaomusic.network.AppHttpClient;
import com.yzx.xiaomusic.network.api.MuiscApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yzx on 2018/1/30.
 * Description
 */

public class VideoCloudModel implements VideoCloudContract.Model<VideoCloudFragment,VideoList> {

    @Override
    public void getVideoList(VideoCloudFragment fragment,int offset, MvpObserver<VideoList> observer) {

        AppHttpClient
                .getInstance()
                .getService(MuiscApi.class)
                .getVideoList(offset,10)
                .compose(fragment.<VideoList>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void getMv(VideoCloudFragment fragment, String id, MvpObserver<MvData> observer) {
        AppHttpClient.getInstance()
                .getService(MuiscApi.class)
                .getMusicMv("mv",id,"")
                .compose(fragment.<MvData>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
