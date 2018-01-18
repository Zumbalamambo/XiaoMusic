package com.yzx.xiaomusic.ui.main.music.local;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.utils.FileUtils;
import com.yzx.xiaomusic.utils.JsonUtils;
import com.yzx.xiaomusic.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yzx on 2018/1/13.
 * Description 获取本地所有音乐信息
 */

public class LocalMusicModel {

    public static final String LOCAL_MUSIC_INFO = "localMusicInfo";

    interface CallBack{
        void onSuccess(List<MusicInfo> list);
        void onNext(MusicInfo musicInfo);
        void onFail(String errorMsg);
    }
    public void getLocalMusicInfo(Context context, final CallBack callBack){
        final List<MusicInfo> list = new ArrayList();
        final Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Audio.AudioColumns.IS_MUSIC);
        if (cursor!=null){
            Observable.create(new ObservableOnSubscribe<MusicInfo>() {
                @Override
                public void subscribe(ObservableEmitter<MusicInfo> e) throws Exception {
                    while (cursor.moveToNext()) {
                  MusicInfo musicInfo = new MusicInfo();
                  musicInfo.allName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));//音乐全名包含歌名和歌手
                  musicInfo.artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));//歌手
                  musicInfo.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));//本地路径
                  musicInfo.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));//歌曲时间
                  musicInfo.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));//歌曲大小
                  musicInfo.poster = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));//海报
                  musicInfo.sha1 = FileUtils.fileToSHA1(musicInfo.getPath());
                  if (musicInfo.size > 1024 * 1024 * 3) {
                      // 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
                      if (musicInfo.allName.contains("-")) {
                          String[] str = musicInfo.allName.split("-");
                          musicInfo.artist = str[0];
                          if (str[1].endsWith(".mp3") || str[1].endsWith(".ape") || str[1].endsWith(".mp3") || str[1].endsWith(".wav") || str[1].endsWith(".flac")) {
                              musicInfo.name = str[1].trim().substring(0, str[1].length() - 5);
                          } else {
                              musicInfo.name = str[1].trim();
                          }
                      }
                      e.onNext(musicInfo);
                  }
              }
              e.onComplete();
                }
            })
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<MusicInfo>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(MusicInfo musicInfo) {
                    list.add(musicInfo);
                    callBack.onNext(musicInfo);
                }
                @Override
                public void onError(Throwable e) {
                    callBack.onFail(e.toString());
                }

                @Override
                public void onComplete() {
                    callBack.onSuccess(list);
                    cursor.close();
                    PreferenceUtil.put(LOCAL_MUSIC_INFO, JsonUtils.objectToString(list));
                }
            });
        }
    }
}
