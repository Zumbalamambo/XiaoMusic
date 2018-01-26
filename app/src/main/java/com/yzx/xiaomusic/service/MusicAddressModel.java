package com.yzx.xiaomusic.service;

import android.text.TextUtils;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.MusicAddress;
import com.yzx.xiaomusic.network.AppHttpClient;
import com.yzx.xiaomusic.network.api.MuiscApi;
import com.yzx.xiaomusic.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * @author yzx
 * @date 2018/1/23
 * Description
 */

public class MusicAddressModel{

    private static MusicAddressModel musicAddressModel;
    private final PlayService playService;

    private MusicAddressModel() {
        playService = PlayServiceManager.getInstance().getPlayService();
    }

    public static MusicAddressModel getInstance() {
        if (musicAddressModel==null){
            musicAddressModel = new MusicAddressModel();
        }
        return musicAddressModel;
    }
    public void getMusicAddress(String id) {
        AppHttpClient
                .getInstance()
                .getService(MuiscApi.class)
                .getMusicAddress("song",id,"320000")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvpObserver<MusicAddress>() {
                    @Override
                    protected void onSuccess(MusicAddress musicAddress) {
                        if (musicAddress.getData().size()>0){
                            MusicAddress.DataBean dataBean = musicAddress.getData().get(0);
                            playService.setMusicAddress(dataBean.getUrl());
                            if (TextUtils.isEmpty(dataBean.getMd5())){
                                ToastUtils.showToast(R.string.noThisMusic,ToastUtils.TYPE_NOTICE);
                            }else {
                                if (dataBean.getMd5().equals(playService.getMd5())){
                                    playService.playMusic();
                                }else {
                                    playService.playCloudMusic(dataBean.getUrl());
                                    playService.setMd5(dataBean.getMd5());
                                }
                            }
                        }else {
                            ToastUtils.showToast(R.string.noThisMusic,ToastUtils.TYPE_NOTICE);
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        super.onFail(errorMsg);
                        ToastUtils.showToast(errorMsg,ToastUtils.TYPE_FAIL);
                    }
                });
    }
}
