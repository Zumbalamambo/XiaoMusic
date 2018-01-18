package com.yzx.xiaomusic.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.yzx.xiaomusic.R;


/**
 * Created by yzx on 2018/1/11.
 * Description
 */

public class LoadingUtils {
    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle(R.string.loading);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}
