package com.yzx.xiaomusic.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.yzx.xiaomusic.utils.LoadingUtils;
import com.yzx.xiaomusic.utils.ResourceUtils;
import com.yzx.xiaomusic.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by yzx on 2018/1/10.
 * Description Activity基类
 */

public abstract class BaseActivity extends SupportActivity implements BaseView {

    private Unbinder bind;
    public Context context;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = LoadingUtils.showLoadingDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        context = this;
        doBeforeSetLayout();
        setContentView(getLayoutId());
        bind = ButterKnife.bind(this);
        initData(savedInstanceState);
        initView(savedInstanceState);
    }

    /**
     * setContentView之前的操作
     */
    public void doBeforeSetLayout() {

    }

    /**
     * 获取LayoutId
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    public void initData(Bundle savedInstanceState) {

    }

    /**
     * 初始化视图
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    @Override
    public void showLoading() {
        if (progressDialog!=null&&!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(int msg) {
        showToast(msg, ToastUtils.TYPE_DEFALUT);
    }

    @Override
    public void showToast(int msg, int type) {
        ToastUtils.showToast(msg,type);
    }

    @Override
    public void showToast(String msg) {
        showToast(msg, ToastUtils.TYPE_DEFALUT);
    }

    @Override
    public void showToast(String msg, int type) {
        ToastUtils.showToast(msg,type);
    }

    @Override
    public void showActionBarTitle(int title) {
        showActionBarTitle(ResourceUtils.parseString(title));
    }

    @Override
    public void showActionBarTitle(String title) {
        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void showBackArrow() {
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    /**
     * 统一处理返回箭头
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (android.R.id.home == item.getItemId()) {
            if (Build.VERSION.SDK_INT>=21){
//                finishAfterTransition();
                onBackPressedSupport();
            }else {
//                finish();
                onBackPressedSupport();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
