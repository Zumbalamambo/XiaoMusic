package com.yzx.xiaomusic.service;

import android.text.TextUtils;
import android.util.Log;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.Constants;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.MusicAddress;
import com.yzx.xiaomusic.network.AppHttpClient;
import com.yzx.xiaomusic.network.api.MuiscApi;
import com.yzx.xiaomusic.utils.ToastUtils;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload3.RxDownload;
import zlc.season.rxdownload3.core.Mission;
import zlc.season.rxdownload3.core.Status;

/**
 *
 * @author yzx
 * @date 2018/1/23
 * Description
 */

public class MusicAddressModel{

    private static final String TAG = "yglMusicAddressModel";
    private static MusicAddressModel musicAddressModel;

    private MusicAddressModel() {

    }

    public static MusicAddressModel getInstance() {
        if (musicAddressModel==null){
            musicAddressModel = new MusicAddressModel();
        }
        return musicAddressModel;
    }
    public void getMusicAddress(final String name, final String id) {
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
                            if (TextUtils.isEmpty(dataBean.getUrl())){
                                ToastUtils.showToast(R.string.error_get_music,ToastUtils.TYPE_NOTICE);
                            }else {
                                PlayServiceManager.getInstance().getPlayService().playCloudMusic(dataBean.getUrl());
                                //保存文件
                                saveMusic(dataBean, name,id);
                            }
                        }else {
                            ToastUtils.showToast(R.string.error_get_music,ToastUtils.TYPE_NOTICE);
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        super.onFail(errorMsg);
                        ToastUtils.showToast(errorMsg,ToastUtils.TYPE_FAIL);
                    }
                });
    }

    private void saveMusic(MusicAddress.DataBean dataBean, String name, String id) {
        File appDir = new File(Constants.PATH_APP);
        if (!appDir.exists()){
            appDir.mkdirs();
        }
        File musicdir = new File(Constants.PATH_ABSOLUTE_MUSIC);
        if (!musicdir.exists()){
            musicdir.mkdirs();
        }
        Mission mission = new Mission(dataBean.getUrl(), name+"-"+id, Constants.PATH_ABSOLUTE_MUSIC);
        RxDownload
                .INSTANCE
                .create(mission)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Status>() {
                    @Override
                    public void accept(Status status) throws Exception {
                        Log.i(TAG, "accept: "+status.percent());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "accept: "+throwable.toString());
                    }
                });
        RxDownload.INSTANCE.start(mission).subscribe();

    }
}
