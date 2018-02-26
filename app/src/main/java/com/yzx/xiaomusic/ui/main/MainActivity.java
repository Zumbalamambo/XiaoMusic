package com.yzx.xiaomusic.ui.main;

import android.os.Bundle;
import android.util.Log;

import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseActivity;
import com.yzx.xiaomusic.db.AppDatabase;
import com.yzx.xiaomusic.db.entity.Music;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private static final String TAG = "yglMainActivity";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        MainFragment fragment = findFragment(MainFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.layout_container, new MainFragment());
        }

//        Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> e) throws Exception {
//
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//
//                    }
//                });
        AppDatabase.getInstance().getMusicDAO().addMuisc(new Music("娃哈哈","杨子晓"));
        AppDatabase
                .getInstance()
                .getMusicDAO()
                .getAllMusic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Music>>() {
                    @Override
                    public void accept(List<Music> music) throws Exception {

                        for (Music m : music) {
                            Log.i(TAG, "initView: "+m.getId()+"："+m.getName()+"-"+m.getArtist());
                        }
                    }
                });
//        Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> e) throws Exception {
//                List<Music> allMusic = AppDatabase.getInstance().getMusicDAO().getAllMusic();
//                for (Music music :
//                        allMusic) {
//                    Log.i(TAG, "initView: "+music.getId()+"："+music.getName()+"-"+music.getArtist());
//                }
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//
//                    }
//                });

    }

    @Override
    protected void onDestroy() {
        //释放所有的视频资源
        GSYVideoPlayer.releaseAllVideos();
        Log.i(TAG, "onDestroy: MAinActivity");
        super.onDestroy();
    }
}
