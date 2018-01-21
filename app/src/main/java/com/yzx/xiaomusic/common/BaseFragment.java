package com.yzx.xiaomusic.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.ui.main.MainActivity;
import com.yzx.xiaomusic.utils.ResourceUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by yzx on 2018/1/12.
 * Description
 */

public abstract class BaseFragment extends SupportFragment {

    private static final String TAG = "yglBaseFragment";
    private Unbinder bind;
    public Context context;
    public boolean isFirstLoad;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        context = getContext();
        Log.i(TAG, "onCreate: "+this.getClass().getSimpleName());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(getLayoutId(), container, false);
        bind = ButterKnife.bind(this, rootView);
        initData(savedInstanceState);
        initView(savedInstanceState);
        Log.i(TAG, "onCreateView: "+this.getClass().getSimpleName());
        return rootView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        Log.i(TAG, "onLazyInitView: "+this.getClass().getSimpleName());
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (!isFirstLoad){
            loadData();
        }
    }

    /**
     * 可见并且第一次加载才数据（懒加载）
     */
    public void loadData() {

    }

    /**
     * 获取布局资源Id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化数据
     * @param savedInstanceState
     */
    public void initData(Bundle savedInstanceState) {

    }
    protected abstract void initView(Bundle savedInstanceState);

    public void setToolBar(Toolbar toolBar, String title){
        setToolBar(toolBar,title, R.drawable.ic_back);
    }
    public void setToolBar(Toolbar toolBar, @StringRes int title){
        setToolBar(toolBar,title, R.drawable.ic_back);
    }
    public void setToolBar(@NonNull Toolbar toolBar, @StringRes int title, @DrawableRes int icon){
        setToolBar(toolBar, ResourceUtils.parseString(title));
    }
    public void setToolBar(@NonNull Toolbar toolBar, String title, @DrawableRes int icon){

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity!=null) {
            mainActivity.setSupportActionBar(toolBar);
            toolBar.setTitle(title);
            toolBar.setNavigationIcon(icon);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
