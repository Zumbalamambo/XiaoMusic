package com.yzx.xiaomusic.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.service.PlayService;
import com.yzx.xiaomusic.service.PlayServiceManager;
import com.yzx.xiaomusic.ui.main.MainActivity;
import com.yzx.xiaomusic.utils.GlideUtils;
import com.yzx.xiaomusic.utils.LoadingUtils;
import com.yzx.xiaomusic.utils.MusicDataUtils;
import com.yzx.xiaomusic.utils.ResourceUtils;
import com.yzx.xiaomusic.utils.ToastUtils;
import com.yzx.xiaomusic.widget.CircleProgress;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by yzx on 2018/1/12.
 * Description
 */

public abstract class BaseFragment extends SupportFragment implements BaseView {

    private static final String TAG = "yglBaseFragment";
    private Unbinder bind;
    public Context context;
    public boolean isFirstLoad;
    public ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        progressDialog = LoadingUtils.showLoadingDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(getLayoutId(), container, false);
        bind = ButterKnife.bind(this, rootView);
        initData(savedInstanceState);
        initView(savedInstanceState);
        return rootView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

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
    public void setToolBar(Toolbar toolBar, String title){
        setToolBar(toolBar,title, null);
    }
    public void setToolBar(Toolbar toolBar, @StringRes int title){
        setToolBar(toolBar,title,-1);
    }

    public void setToolBar(@NonNull Toolbar toolBar, @StringRes int title,@StringRes int subTitle){
        setToolBar(toolBar, ResourceUtils.parseString(title));
    }

    public void setToolBar(@NonNull Toolbar toolBar, String title,String subTitle){

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity!=null) {
            mainActivity.setSupportActionBar(toolBar);
            mainActivity.getSupportActionBar().setTitle(null);
            TextView tvTitle = (TextView) toolBar.findViewById(R.id.tv_title);
            TextView tvSubTitle = (TextView) toolBar.findViewById(R.id.tv_subtitle);
            tvTitle.setText(title);
            if (TextUtils.isEmpty(subTitle)){
                if (tvSubTitle!=null){
                    tvSubTitle.setVisibility(View.GONE);
                }
                tvTitle.setTextSize(16);
            }else {
                if (tvSubTitle!=null){
                    tvSubTitle.setVisibility(View.VISIBLE);
                    tvSubTitle.setText(subTitle);
                }
                tvTitle.setTextSize(14);
            }

        }
    }



    /**
     * 获取playService
     * @return
     */
    public PlayService getPlayService(){
        PlayService playService = PlayServiceManager.getInstance().getPlayService();
        if (playService==null){
            throw new NullPointerException("playService can't be null");
        }
        return playService;
    }

    public void setUpBottomPlayControl(TextView tvMusicName, TextView tvMusicArtist, CircleProgress circleProgress, ImageView ivMusicPoster) {

        Object musicInfo = getPlayService().getMusicInfo();
        Log.i(TAG, this.getClass().getSimpleName()+"setUpBottomPlayControl: "+MusicDataUtils.getMusicName(musicInfo));
        tvMusicName.setText(MusicDataUtils.getMusicName(musicInfo));
        tvMusicArtist.setText(MusicDataUtils.getMusicArtist(musicInfo));
        circleProgress.setState(PlayService.STATE_PLAYING==getPlayService().getState()?CircleProgress.STATE_PLAY:CircleProgress.STATE_PAUSE);
        GlideUtils.loadImg(context,MusicDataUtils.getMusicPoster(musicInfo),GlideUtils.TYPE_DEFAULT,ivMusicPoster);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.i(TAG, "onDestroy: "+this.getClass().getSimpleName());
        bind.unbind();
    }
}
