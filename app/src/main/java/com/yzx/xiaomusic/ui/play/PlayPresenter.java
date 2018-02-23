package com.yzx.xiaomusic.ui.play;

import android.util.Log;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.Constants;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.common.observel.OtherObserver;
import com.yzx.xiaomusic.entities.Lyric;
import com.yzx.xiaomusic.entities.SearchResult;
import com.yzx.xiaomusic.utils.ToastUtils;

import java.io.File;

/**
 *
 * @author yzx
 * @date 2018/2/5
 * Description
 */

public class PlayPresenter implements PlayContract.Presenter {

    private static final String TAG = "yglPlayPresenter";
    private final PlayFragment fragment;
    private final PlayModel model;

    public PlayPresenter(PlayFragment fragment) {
        this.fragment = fragment;
        model = new PlayModel();
    }

    @Override
    public void getLyrics(final String id) {

        model.getLyrics(fragment, id, new OtherObserver<Lyric>() {
            @Override
            protected void onSuccess(Lyric lyric) {
                fragment.lyricView.setLyricFile(new File(Constants.PATH_ABSOLUTE_CACHE_LYRIC+ "/"+id));
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);
                Log.i(TAG, "onFail: "+errorMsg);
            }
        });
    }

    @Override
    public void getLocalMusicLyrics(String musicName) {
        model.getLgetLocalMusicLyricsyrics(fragment, musicName, new MvpObserver<SearchResult>() {
            @Override
            protected void onSuccess(SearchResult searchResult) {
                if (searchResult!=null&&searchResult.getResult()!=null&&searchResult.getResult().getSongs().size()>0){

                    String id = String.valueOf(searchResult.getResult().getSongs().get(0).getId());
                    fragment.loadMusicLyrics(id);
//                    getLyrics(id);
                }else {
                    fragment.showToast(R.string.error_get_lyric, ToastUtils.TYPE_FAIL);
                }
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);
                Log.i(TAG, "onFail: "+errorMsg);
            }
        });
    }
}
