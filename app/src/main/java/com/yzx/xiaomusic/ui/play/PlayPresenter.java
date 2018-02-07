package com.yzx.xiaomusic.ui.play;

import android.util.Log;

import com.yzx.xiaomusic.common.Constants;
import com.yzx.xiaomusic.common.observel.OtherObserver;
import com.yzx.xiaomusic.entities.Lyric;

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
                fragment.lyricView.setLyricFile(new File(Constants.PATH_ABSOLUTE_LYRIC+ "/"+id));
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);
                Log.i(TAG, "onFail: "+errorMsg);
            }
        });
    }
}