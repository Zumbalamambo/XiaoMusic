package com.yzx.xiaomusic.ui.artistcenter;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.ArtistCenterInfo;
import com.yzx.xiaomusic.network.AppHttpClient;
import com.yzx.xiaomusic.network.api.ArtistApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yzx on 2018/2/2.
 * Description
 */

public class ArtistCenterModel implements ArtistCenterContract.Model<ArtistCenterFragment,ArtistCenterInfo> {

    @Override
    public void getArtistInfo(ArtistCenterFragment artistCenterFragment, String artistId, MvpObserver<ArtistCenterInfo> observer) {
        AppHttpClient
                .getInstance()
                .getService(ArtistApi.class)
                .getArtistCenterInfo(artistId)
                .compose(artistCenterFragment.<ArtistCenterInfo>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
