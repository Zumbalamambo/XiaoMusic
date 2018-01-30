package com.yzx.xiaomusic.common.mvp.factory;


import com.yzx.xiaomusic.common.mvp.BaseMvpView;
import com.yzx.xiaomusic.common.mvp.presenter.BaseMvpPresenter;

/**
 * @author 刘镓旗
 * @date 2017/11/17
 * @description Presenter工厂接口
 */
public interface PresenterMvpFactory<V extends BaseMvpView,P extends BaseMvpPresenter<V>> {

    /**
     * 创建Presenter的接口方法
     * @return 需要创建的Presenter
     */
    P createMvpPresenter();
}
