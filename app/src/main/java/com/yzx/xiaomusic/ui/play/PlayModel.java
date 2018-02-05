package com.yzx.xiaomusic.ui.play;

import com.yzx.xiaomusic.MusicApplication;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.Lyric;
import com.yzx.xiaomusic.network.AppHttpClient;
import com.yzx.xiaomusic.network.api.MuiscApi;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yzx on 2018/2/5.
 * Description
 */

public class PlayModel implements PlayContract.Model<PlayFragment> {

    @Override
    public void getLyrics(PlayFragment playFragment, String id, MvpObserver<Lyric> observer) {
        AppHttpClient
                .getInstance()
                .getService(MuiscApi.class)
                .getMusicLyrics("lyric",id)
                .compose(playFragment.<Lyric>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void saveToFile(final String id, final String lyric, Observer<String> observer) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                File file = new File(MusicApplication.getApplication().getCacheDir(),id);
                try {
                    FileOutputStream fileOutputStream=new FileOutputStream(file);
                    fileOutputStream.write(lyric.getBytes());
                    fileOutputStream.close();
                    e.onComplete();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    e.onError(ex);
                }
            }
        }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
