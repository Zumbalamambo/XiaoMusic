package com.yzx.xiaomusic.ui.mv;

import com.yzx.xiaomusic.common.mvp.BaseMvpView;

/**
 * Created by yzx on 2018/1/29.
 * Description
 */

public interface MvView extends BaseMvpView{
    /**
     * 获取Mv数据
     */
    void getMvData(String mvId);
}
