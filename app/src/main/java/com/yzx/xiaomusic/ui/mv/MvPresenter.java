package com.yzx.xiaomusic.ui.mv;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.MvData;
import com.yzx.xiaomusic.utils.ToastUtils;

/**
 *
 * @author yzx
 * @date 2018/1/29
 * Description
 */

public class MvPresenter implements MvContract.Presenter{

    private final MvModel mMvModel;
    private final MvFragment fragment;

    public MvPresenter(MvFragment fragment) {
        this.fragment = fragment;
        mMvModel = new MvModel();
    }

    @Override
    public void getMvAddress(String mvId) {

        mMvModel.getMvAddress(fragment, mvId, new MvpObserver<MvData>() {
            @Override
            protected void onSuccess(MvData mvData) {
                fragment.setData(mvData);
            }

            @Override
            protected void onFail(String errorMsg) {
                super.onFail(errorMsg);
                fragment.showToast(errorMsg, ToastUtils.TYPE_FAIL);
            }
        });
    }
}
