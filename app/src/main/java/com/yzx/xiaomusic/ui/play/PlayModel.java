package com.yzx.xiaomusic.ui.play;

import com.yzx.xiaomusic.common.Constants;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.common.observel.OtherObserver;
import com.yzx.xiaomusic.entities.Lyric;
import com.yzx.xiaomusic.entities.SearchResult;
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
                        if (lyric!=null&&lyric.getLrc()!=null&&lyric.getLrc().getLyric()!=null){
//                            Log.i(TAG, "accept: 获取歌词成功"+lyric.getLrc().getLyric());
                        }else {

                        }
                    }
                })
                .flatMap(new Function<Lyric, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(final Lyric lyric) throws Exception {
                        return new Observable<Lyric>(){
                            @Override
                            protected void subscribeActual(Observer<? super Lyric> observer) {
                                if (lyric!=null&&lyric.getLrc()!=null&&lyric.getLrc().getLyric()!=null){

                                    File appDir = new File(Constants.PATH_APP);
                                    if (!appDir.exists()){
                                        appDir.mkdirs();
                                    }
                                    File lyricDir = new File(Constants.PATH_ABSOLUTE_LYRIC);
                                    if (!lyricDir.exists()){
                                        lyricDir.mkdirs();
                                    }
                                    File file = new File(Constants.PATH_ABSOLUTE_LYRIC+ "/" + id);
                                    try {
                                        FileOutputStream fileOutputStream=new FileOutputStream(file);
                                        fileOutputStream.write(lyric.getLrc().getLyric().getBytes());
                                        fileOutputStream.close();
                                        observer.onNext(lyric);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        observer.onError(ex);
                                    }
                                }else {
                                    observer.onError(new NullPointerException("have no lyric"));
                                }
                            }
                        };
                    }
                })
                .compose(playFragment.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void getLgetLocalMusicLyricsyrics(PlayFragment fragment, String musicName, MvpObserver<SearchResult> observer) {

        AppHttpClient
                .getInstance()
                .getService(MuiscApi.class)
                .searchMusic("1",musicName,0,1)
                .compose(fragment.<SearchResult>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
