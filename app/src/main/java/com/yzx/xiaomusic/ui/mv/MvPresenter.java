package com.yzx.xiaomusic.ui.mv;

import android.util.Log;

import com.yzx.xiaomusic.common.mvp.presenter.BaseMvpPresenter;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.MvData;

import io.reactivex.disposables.Disposable;

/**
 *
 * @author yzx
 * @date 2018/1/29
 * Description
 */

public class MvPresenter extends BaseMvpPresenter<MvView> {

    private final MvModel mMvModel;
    private Disposable d;

    public MvPresenter() {
        mMvModel = new MvModel();
    }

    public void getMvData(String mvId){
        mMvModel.getMvData(mvId, new MvpObserver<MvData>() {

            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                MvPresenter.this.d = d;
            }

            @Override
            protected void onSuccess(MvData mvData) {

                Log.i("yglMv", "onSuccess: "+mvData.getData().getName());
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);
                Log.i("yglMv", "onFail: "+errorMsg);
            }
        });
    }

    @Override
    public void onDestroyPersenter() {
        super.onDestroyPersenter();
        if (d!=null){
            d.dispose();
            Log.i("yglMv", "onDestroyPersenter: 取消请求");
        }
    }
}
