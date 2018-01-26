package com.yzx.xiaomusic.common;

import android.support.annotation.StringRes;

/**
 * @author yzx
 * @date 2018/1/10
 * Description
 */

public interface BaseView {
    void showLoading();

    void hideLoading();

    void showToast(@StringRes int msg, int type);
    void showToast(@StringRes int msg);
    void showToast(String msg, int type);
    void showToast(String msg);
//    void showActionBarTitle(@StringRes int title);
//    void showActionBarTitle(String title);
//    void showBackArrow();

}
