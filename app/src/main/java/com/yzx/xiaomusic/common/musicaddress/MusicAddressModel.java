package com.yzx.xiaomusic.common.musicaddress;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.MusicAddress;
import com.yzx.xiaomusic.network.AppHttpClient;
import com.yzx.xiaomusic.network.api.MuiscApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * @author yzx
 * @date 2018/1/23
 * Description
 */

public class MusicAddressModel implements MusicAddressContract.Model {
    @Override
    public void getMusicAddress(String id, MvpObserver<MusicAddress> observer) {
        AppHttpClient
                .getInstance()
                .getService(MuiscApi.class)
                .getMusicAddress("song",id,320000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
