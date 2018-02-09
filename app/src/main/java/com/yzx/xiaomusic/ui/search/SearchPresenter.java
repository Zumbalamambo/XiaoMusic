package com.yzx.xiaomusic.ui.search;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.SearchResult;

/**
 * Created by yzx on 2018/2/9.
 * Description
 */

public class SearchPresenter implements SearchContract.Presenter {

    private final SearchFragment searchFragment;
    private final SearchModel model;

    public SearchPresenter(SearchFragment searchFragment) {
        this.searchFragment = searchFragment;
        model = new SearchModel();
    }

    @Override
    public void search(String keyWord) {
        model.search(searchFragment, keyWord, new MvpObserver<SearchResult>() {
            @Override
            protected void onSuccess(SearchResult searchResult) {
//                Log.i(TAG, "onSuccess: "+searchResult.getResult().getSongs().get(0).getName());
                searchFragment.adapter.setDatas(searchResult);
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);
            }
        });
    }
}
