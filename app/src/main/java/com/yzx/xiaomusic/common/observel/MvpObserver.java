package com.yzx.xiaomusic.common.observel;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.entities.BaseResposeBody;
import com.yzx.xiaomusic.utils.LoadingUtils;
import com.yzx.xiaomusic.utils.ResourceUtils;
import com.yzx.xiaomusic.utils.ToastUtils;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yzx on 2017/11/27.
 */

public abstract class MvpObserver<B extends BaseResposeBody> implements Observer<B> {


    private boolean showDialog;
    private Dialog dialog;
    public static final String TAG ="yglMvpObserver";

    public MvpObserver() {

    }

    public MvpObserver(Context context, boolean showDialog) {
        this.showDialog = showDialog;
        if (showDialog){
            dialog = LoadingUtils.showLoadingDialog(context);
            dialog.show();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(B value) {

        if (200!=value.getCode()){
            Log.i(TAG, "onNext: "+value.getCode());
            onFail(ResourceUtils.parseString(R.string.error_get_data));
        }else {
            onSuccess(value);
        }
    }

    @Override
    public void onError(Throwable e) {
        onException(e);
        if (showDialog&&dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void onComplete() {
        if (showDialog&&dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }

    protected abstract void onSuccess(B b);

    protected void onFail(String errorMsg){
        ToastUtils.showToast(errorMsg, ToastUtils.TYPE_FAIL);
    }
    private void onException(Throwable e) {
        if (e instanceof HttpException) {     //   HTTP错误
//            ToastUtils.showToast("HTTP错误"+e.toString(),ToastUtils.TYPE_FAIL);
            Log.i(TAG, "onException: "+"HTTP错误"+e.toString());
            onFail(ResourceUtils.parseString(R.string.error_http));
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            Log.i(TAG, "onException: "+"连接错误"+e.toString());
            onFail(ResourceUtils.parseString(R.string.error_connect));
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            Log.i(TAG, "onException: "+"连接超时"+e.toString());
            onFail(ResourceUtils.parseString(R.string.error_connect_time_out));
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            Log.i(TAG, "onException: "+"解析错误"+e.toString());
            onFail(ResourceUtils.parseString(R.string.error_parse));
        } else {
            Log.i(TAG, "onException: "+"else错误"+e.toString());
//            ToastUtils.showToast("else错误"+e.toString(),ToastUtils.TYPE_FAIL);
            onFail(ResourceUtils.parseString(R.string.error_else));
        }
    }
}
