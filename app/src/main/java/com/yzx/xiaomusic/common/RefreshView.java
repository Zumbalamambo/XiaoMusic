package com.yzx.xiaomusic.common;

import com.yzx.xiaomusic.entities.BaseResposeBody;

/**
 * Created by yzx on 2018/1/20.
 * Description  刷新
 */

public interface RefreshView<B extends BaseResposeBody> {
    void onRefreshSuccess(B b);
    void onRefreshFail(String errorMsg);
    void onLoadMoreSuccess(B b);
    void onLoadMoreFail(String errorMsg);
}
