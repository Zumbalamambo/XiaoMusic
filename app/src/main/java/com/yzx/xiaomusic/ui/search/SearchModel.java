package com.yzx.xiaomusic.ui.search;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.SearchResult;
import com.yzx.xiaomusic.network.AppHttpClient;
import com.yzx.xiaomusic.network.api.MuiscApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yzx on 2018/2/9.
 * Description
 */

public class SearchModel implements SearchContract.Model<SearchFragment,SearchResult> {
    @Override
    public void search(SearchFragment searchFragment, String keyWord, MvpObserver<SearchResult> observer) {
        AppHttpClient
                .getInstance()
                .getService(MuiscApi.class)
                .searchMusic("1",keyWord,0,10)
                .compose(searchFragment.<SearchResult>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
