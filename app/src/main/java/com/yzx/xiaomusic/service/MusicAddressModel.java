package com.yzx.xiaomusic.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.cache.CacheUtils;
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
 * @author yzx
 * @date 2018/1/23
 * Description
 */

public class MusicAddressModel {

    private static final String TAG = "yglMusicAddressModel";
    private static MusicAddressModel musicAddressModel;

    private MusicAddressModel() {

    }

    public static MusicAddressModel getInstance() {
        if (musicAddressModel == null) {
            musicAddressModel = new MusicAddressModel();
        }
        return musicAddressModel;
    }

    public void getMusicAddress(final String name, final String id) {
        AppHttpClient
                .getInstance()
                .getService(MuiscApi.class)
                .getMusicAddress("song", id, 320000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvpObserver<MusicAddress>() {
                    @Override
                    protected void onSuccess(MusicAddress musicAddress) {
                        if (musicAddress.getData().size() > 0) {
                            MusicAddress.DataBean dataBean = musicAddress.getData().get(0);
                            if (TextUtils.isEmpty(dataBean.getUrl())) {
                                ToastUtils.showToast(R.string.error_get_music, ToastUtils.TYPE_NOTICE);
                            } else {
                                PlayServiceManager.getInstance().getPlayService().playCloudMusic(dataBean.getUrl());

                                //缓存音乐文件
                                CacheUtils.cacheMusic(id,dataBean.getUrl(),dataBean.getMd5());
                            }
                        } else {
                            ToastUtils.showToast(R.string.error_get_music, ToastUtils.TYPE_NOTICE);
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        super.onFail(errorMsg);
                        ToastUtils.showToast(errorMsg, ToastUtils.TYPE_FAIL);
                    }
                });
    }


    public void downloadMusic(final Context context, final String name, final String artist, final String id, int rb) {
        AppHttpClient
                .getInstance()
                .getService(MuiscApi.class)
                .getMusicAddress("song", id, rb)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvpObserver<MusicAddress>() {
                    @Override
                    protected void onSuccess(MusicAddress musicAddress) {
                        if (musicAddress.getData().size() > 0) {
                            MusicAddress.DataBean dataBean = musicAddress.getData().get(0);
                            if (TextUtils.isEmpty(dataBean.getUrl())) {
                                ToastUtils.showToast(R.string.error_get_music, ToastUtils.TYPE_NOTICE);
                            } else {
                                download(context,dataBean,artist,name);
                            }
                        } else {
                            ToastUtils.showToast(R.string.error_get_music, ToastUtils.TYPE_NOTICE);
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        super.onFail(errorMsg);
                        ToastUtils.showToast(errorMsg, ToastUtils.TYPE_FAIL);
                    }
                });
    }

    /**
     * 下载音乐
     * @param context
     * @param dataBean
     * @param artist
     * @param name
     */
    private void download(final Context context, MusicAddress.DataBean dataBean, String artist, String name) {
        File appDir = new File(Constants.PATH_APP);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        File musicdir = new File(Constants.PATH_ABSOLUTE_DOWNLOAD);
        if (!musicdir.exists()) {
            musicdir.mkdirs();
        }
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        Mission mission = new Mission(dataBean.getUrl(), name + " - "+artist + ".mp3", Constants.PATH_ABSOLUTE_DOWNLOAD);
        RxDownload
                .INSTANCE
                .create(mission)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Status>() {
                    @Override
                    public void accept(Status status) throws Exception {
                        String percent = status.percent();
                        Log.i(TAG, "accept: " + percent);

                        if (status.getTotalSize() != 0) {
                            progressDialog.setProgress((int) (status.getDownloadSize() * 100 / status.getTotalSize()));
                        }
                        if ("0.00%".equals(percent)) {
                            progressDialog.show();
                        } else if ("100.00%".equals(percent)) {
                            progressDialog.dismiss();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "accept: " + throwable.toString());
                    }
                });
        RxDownload.INSTANCE.start(mission).subscribe();
    }

}
