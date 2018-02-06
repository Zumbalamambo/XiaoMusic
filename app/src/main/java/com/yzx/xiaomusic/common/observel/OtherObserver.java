package com.yzx.xiaomusic.common.observel;

import android.app.Dialog;
import android.content.Context;

import com.yzx.xiaomusic.utils.LoadingUtils;
import com.yzx.xiaomusic.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yzx on 2017/11/27.
 */

public abstract class OtherObserver<B extends Object> implements Observer<B> {


    private boolean showDialog;
    private Dialog dialog;
    public static final String TAG ="yglMvpObserver";

    public OtherObserver() {

    }

    public OtherObserver(Context context, boolean showDialog) {
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

        onSuccess(value);
    }

    @Override
    public void onError(Throwable e) {
        onFail(e.toString());
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
//    private void onException(Throwable e) {
//        if (e instanceof HttpException) {     //   HTTP错误
////            ToastUtils.showToast("HTTP错误"+e.toString(),ToastUtils.TYPE_FAIL);
//            Log.i(TAG, "onException: "+"HTTP错误"+e.toString());
//            onFail(ResourceUtils.parseString(R.string.error_http));
//        } else if (e instanceof ConnectException
//                || e instanceof UnknownHostException) {   //   连接错误
//            Log.i(TAG, "onException: "+"连接错误"+e.toString());
//            onFail(ResourceUtils.parseString(R.string.error_connect));
//        } else if (e instanceof InterruptedIOException) {   //  连接超时
//            Log.i(TAG, "onException: "+"连接超时"+e.toString());
//            onFail(ResourceUtils.parseString(R.string.error_connect_time_out));
//        } else if (e instanceof JsonParseException
//                || e instanceof JSONException
//                || e instanceof ParseException) {   //  解析错误
//            Log.i(TAG, "onException: "+"解析错误"+e.toString());
//            onFail(ResourceUtils.parseString(R.string.error_parse));
//        } else {
//            Log.i(TAG, "onException: "+"else错误"+e.toString());
////            ToastUtils.showToast("else错误"+e.toString(),ToastUtils.TYPE_FAIL);
//            onFail(ResourceUtils.parseString(R.string.error_else));
//        }
//    }
}
