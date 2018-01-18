package com.yzx.xiaomusic.ui.main.music.local;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.utils.DensityUtils;
import com.yzx.xiaomusic.utils.ToastUtils;

import java.util.List;

/**
 * Created by yzx on 2018/1/13.
 * Description
 */

public class LocalMusicPresenter {

    private final LocalMusicActivity mActivity;
    private final LocalMusicModel mModel;

    public LocalMusicPresenter(LocalMusicActivity activity) {
        mActivity = activity;
        mModel = new LocalMusicModel();
    }

    public void getLocalMusicInfo(Context context) {
        mModel.getLocalMusicInfo(context, new LocalMusicModel.CallBack() {
            @Override
            public void onSuccess(List<MusicInfo> list) {
                mActivity.adapter.setDatas(list);
                mActivity.tvScanningMusicName.animate().translationY(-DensityUtils.dip2px(50)).setDuration(1000).setInterpolator(new AccelerateInterpolator()).start();
            }

            @Override
            public void onNext(MusicInfo musicInfo) {
                mActivity.tvScanningMusicName.setText(musicInfo.getName());
                Log.i("lll", "onNext: "+musicInfo.getName());
            }

            @Override
            public void onFail(String errorMsg) {
                mActivity.showToast(errorMsg, ToastUtils.TYPE_FAIL);
                mActivity.tvScanningMusicName.setVisibility(View.INVISIBLE);
            }
        });
    }
}
