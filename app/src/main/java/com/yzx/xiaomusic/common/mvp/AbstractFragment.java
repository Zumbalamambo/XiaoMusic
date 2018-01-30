package com.yzx.xiaomusic.common.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yzx.xiaomusic.common.mvp.factory.PresenterMvpFactory;
import com.yzx.xiaomusic.common.mvp.factory.PresenterMvpFactoryImpl;
import com.yzx.xiaomusic.common.mvp.presenter.BaseMvpPresenter;
import com.yzx.xiaomusic.common.mvp.proxy.BaseMvpProxy;
import com.yzx.xiaomusic.common.mvp.proxy.PresenterProxyInterface;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * @author 刘镓旗
 * @date 2017/11/20
 * @description 继承Fragment的MvpFragment基类
 */
public class AbstractFragment<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends SupportFragment implements PresenterProxyInterface<V, P> {

    /**
     * 调用onSaveInstanceState时存入Bundle的key
     */
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<V, P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V, P>createFactory(getClass()));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            mProxy.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mProxy.onResume((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY,mProxy.onSaveInstanceState());
    }





    /**
     * 可以实现自己PresenterMvpFactory工厂
     *
     * @param presenterFactory PresenterFactory类型
     */
    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) {
        mProxy.setPresenterFactory(presenterFactory);
    }


    /**
     * 获取创建Presenter的工厂
     *
     * @return PresenterMvpFactory类型
     */
    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        return mProxy.getPresenterFactory();
    }

    /**
     * 获取Presenter
     * @return P
     */
    @Override
    public P getMvpPresenter() {
        return mProxy.getMvpPresenter();
    }
}
