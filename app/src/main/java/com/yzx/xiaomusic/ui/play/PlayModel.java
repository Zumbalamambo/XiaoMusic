package com.yzx.xiaomusic.ui.play;

import android.util.Log;

import com.yzx.xiaomusic.MusicApplication;
import com.yzx.xiaomusic.common.observel.OtherObserver;
import com.yzx.xiaomusic.entities.Lyric;
import com.yzx.xiaomusic.network.AppHttpClient;
import com.yzx.xiaomusic.network.api.MuiscApi;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yzx on 2018/2/5.
 * Description
 */

public class PlayModel implements PlayContract.Model<PlayFragment> {

    private static final String TAG = "yglPlayModel";

    @Override
    public void getLyrics(PlayFragment playFragment, final String id, OtherObserver observer) {

        AppHttpClient
                .getInstance()
                .getService(MuiscApi.class)
                .getMusicLyrics("lyric",id)
                .compose(playFragment.<Lyric>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Lyric>() {
                    @Override
                    public void accept(Lyric lyric) throws Exception {
                        Log.i(TAG, "accept: 获取歌词成功");
                    }
                })
                .flatMap(new Function<Lyric, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(final Lyric lyric) throws Exception {
                        return new Observable<Lyric>(){
                            @Override
                            protected void subscribeActual(Observer<? super Lyric> observer) {
                                if (lyric!=null&&lyric.getLrc()!=null&&lyric.getLrc().getLyric()!=null){

                                    File file = new File(MusicApplication.getApplication().getCacheDir(),id);
                                    try {
                                        FileOutputStream fileOutputStream=new FileOutputStream(file);
                                        fileOutputStream.write(lyric.getLrc().getLyric().getBytes());
                                        fileOutputStream.close();
                                        observer.onComplete();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        observer.onError(ex);
                                    }
                                }else {
                                    observer.onError(new NullPointerException("lyric为空"));
                                }
                            }
                        };
                    }
                })
                .compose(playFragment.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(observer);
    }
}
