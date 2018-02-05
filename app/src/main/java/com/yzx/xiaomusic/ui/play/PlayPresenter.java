package com.yzx.xiaomusic.ui.play;

import android.util.Log;

import com.yzx.xiaomusic.MusicApplication;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.Lyric;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yzx on 2018/2/5.
 * Description
 */

public class PlayPresenter implements PlayContract.Presenter {

    private final PlayFragment fragment;
    private final PlayModel model;

    public PlayPresenter(PlayFragment fragment) {
        this.fragment = fragment;
        model = new PlayModel();
    }

    @Override
    public void getLyrics(final String id) {

        model.getLyrics(fragment, id, new MvpObserver<Lyric>() {
            @Override
            protected void onSuccess(Lyric lyric) {
//                fragment.lyricView.se
//                Log.i(TAG, "onSuccess: "+lyric.getLrc().getLyric());
                if (lyric!=null&&lyric.getLrc()!=null&&lyric.getLrc().getLyric()!=null){

                    model.saveToFile(id, lyric.getLrc().getLyric(), new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String s) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            fragment.lyricView.setLyricFile(new File(MusicApplication.getApplication().getCacheDir(),id));
                        }
                    });
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
