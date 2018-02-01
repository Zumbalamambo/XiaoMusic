package com.yzx.xiaomusic.ui.mv;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.MvData;
import com.yzx.xiaomusic.network.AppHttpClient;
import com.yzx.xiaomusic.network.api.MuiscApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yzx on 2018/1/29.
 * Description
 */

public class MvModel implements MvContract.Model<MvFragment,MvData>{

    @Override
    public void getMvAddress(MvFragment fragment, String mvId, MvpObserver<MvData> observer) {
        AppHttpClient.getInstance()
                .getService(MuiscApi.class)
                .getMusicMv("mv",mvId,"")
                .compose(fragment.<MvData>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
