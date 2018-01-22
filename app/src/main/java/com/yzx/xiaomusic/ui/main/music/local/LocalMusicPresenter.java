package com.yzx.xiaomusic.ui.main.music.local;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.ui.adapter.CommonMusicAdapter;
import com.yzx.xiaomusic.utils.DensityUtils;

import java.util.List;

/**
 * Created by yzx on 2018/1/13.
 * Description
 */

public class LocalMusicPresenter {

    private final LocalMusicModel mModel;
    private final LocalMusicFragment mFragment;

    public LocalMusicPresenter(LocalMusicFragment fragment) {
        mFragment = fragment;
        mModel = new LocalMusicModel();
    }

    public void getLocalMusicInfo(Context context) {
        mModel.getLocalMusicInfo(context, new LocalMusicModel.CallBack() {
            @Override
            public void onSuccess(List<MusicInfo> list) {
                mFragment.adapter.setDatas(CommonMusicAdapter.DATA_TYPE_LOCAL_MUSIC,list);
                mFragment.tvScanningMusicName.animate().translationY(-DensityUtils.dip2px(50)).setDuration(1000).setInterpolator(new AccelerateInterpolator()).start();
            }

            @Override
            public void onNext(MusicInfo musicInfo) {
                mFragment.tvScanningMusicName.setText(musicInfo.getName());
                Log.i("lll", "onNext: "+musicInfo.getName());
            }

            @Override
            public void onFail(String errorMsg) {
//                mFragment.showToast(errorMsg, ToastUtils.TYPE_FAIL);
                mFragment.tvScanningMusicName.setVisibility(View.INVISIBLE);
            }
        });
    }
}
