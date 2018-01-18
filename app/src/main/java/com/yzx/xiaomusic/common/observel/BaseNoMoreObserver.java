package com.yzx.xiaomusic.common.observel;

import android.app.Activity;

import com.yzx.xiaomusic.entities.BaseResposeBody;


/**
 * Created by yzx on 2017/11/27.
 */

public abstract class BaseNoMoreObserver<T extends BaseResposeBody> extends BaseObserver<T> {

    public BaseNoMoreObserver(Activity activity, boolean showDialog) {
        super(activity,showDialog);
    }

    @Override
    protected void onFail(int code, String errorMsg) {
        if (code==11){
            onNoMore();
        }else {
            super.onFail(code,errorMsg);
        }
    }

    protected abstract void onNoMore();
}
