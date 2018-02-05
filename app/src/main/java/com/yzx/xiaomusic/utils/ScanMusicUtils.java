package com.yzx.xiaomusic.utils;

import android.text.TextUtils;

import com.yzx.xiaomusic.entities.MusicInfo;

import java.util.List;

/**
 * Created by yzx on 2018/1/11.
 * Description
 */

public class ScanMusicUtils {
    public static final String LOCAL_MUSIC_INFO = "localMusicInfo";

//    public static List<MusicInfo> getLocalMusic(final Context context) {


//        final List<MusicInfo> list = new ArrayList();
//        // 媒体库查询语句（写一个工具类MusicUtils）
//        final Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
//                null, MediaStore.Audio.AudioColumns.IS_MUSIC);
//        if (cursor!=null){
//                Observable.create(new ObservableOnSubscribe<MusicInfo>() {
//                   @Override
//                   public void subscribe(ObservableEmitter<MusicInfo> e) throws Exception {
//                       while (cursor.moveToNext()) {
//                           MusicInfo musicInfo = new MusicInfo();
//                           musicInfo.name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
//                           musicInfo.artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
//                           musicInfo.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
//                           musicInfo.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
//                           musicInfo.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
//                           String albumid = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
//                           musicInfo.poster = CommonUtil.getMusicBitemp(context, Long.parseLong(albumid));//海报
//                           musicInfo.md5 = FileUtils.fileToMD5(musicInfo.getPath());
//                           if (musicInfo.size > 1024 * 1024 * 3) {
//                               // 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
//                               if (musicInfo.name.contains("-")) {
//                                   String[] str = musicInfo.name.split("-");
//                                   musicInfo.artist = str[0];
//                                   if (str[1].endsWith(".mp3") || str[1].endsWith(".ape") || str[1].endsWith(".mp3") || str[1].endsWith(".wav") || str[1].endsWith(".flac")) {
//                                       musicInfo.name = str[1].trim().substring(0, str[1].length() - 4);
//                                   } else {
//                                       musicInfo.name = str[1].trim();
//                                   }
//                               }
//                              e.onNext(musicInfo);
//                           }
//                       }
//                   }
//                })
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<MusicInfo>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(MusicInfo musicInfo) {
//                            list.add(musicInfo);
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        cursor.close();
//                        PreferenceUtil.put(LOCAL_MUSIC_INFO, JsonUtils.objectToString(list));
//                    }
//                });
//        }
//        return list;
//    }

    /**
     * 获取sp文件里的本地音乐信息
     * @return
     */
    public static List<MusicInfo> getLocalMusicInfoByPreference() {
        String s = PreferenceUtil.getString(LOCAL_MUSIC_INFO, null);
        if(TextUtils.isEmpty(s)){
            return null;
        }else {
            return JsonUtils.stringToList(s,MusicInfo.class);
        }
    }
}
