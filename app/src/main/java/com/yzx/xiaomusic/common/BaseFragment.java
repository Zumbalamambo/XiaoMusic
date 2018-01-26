package com.yzx.xiaomusic.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.service.PlayService;
import com.yzx.xiaomusic.service.PlayServiceManager;
import com.yzx.xiaomusic.ui.main.MainActivity;
import com.yzx.xiaomusic.ui.play.PlayFragment;
import com.yzx.xiaomusic.utils.LoadingUtils;
import com.yzx.xiaomusic.utils.ResourceUtils;
import com.yzx.xiaomusic.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by yzx on 2018/1/12.
 * Description
 */

public abstract class BaseFragment extends SupportFragment implements View.OnClickListener ,BaseView{

    private static final String TAG = "yglBaseFragment";
    private Unbinder bind;
    public Context context;
    public boolean isFirstLoad;
    private ImageView musicPoster;
    private TextView musicName;
    private TextView musicArtist;
    private ImageView ivMusicPlay;
    private ImageView ivMusicMenu;
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
//        Log.i(TAG, "onCreateView: "+this.getClass().getSimpleName());
        return rootView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

//        Log.i(TAG, "onLazyInitView: "+this.getClass().getSimpleName());
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
                tvSubTitle.setVisibility(View.GONE);
                tvTitle.setTextSize(16);
            }else {
                tvSubTitle.setVisibility(View.VISIBLE);
                tvSubTitle.setText(subTitle);
                tvTitle.setTextSize(14);
            }

        }
    }

    /**
     * 初始化底部的音乐播放控制控件
     * @param musicControl 音乐控件
     *
     */
    public void initPlayWidget(LinearLayout musicControl){
        musicPoster = (ImageView) musicControl.findViewById(R.id.iv_music_poster);
        musicName = (TextView) musicControl.findViewById(R.id.tv_music_name);
        musicName.setSelected(true);
        musicArtist = (TextView) musicControl.findViewById(R.id.tv_music_artist);
        ivMusicPlay = (ImageView) musicControl.findViewById(R.id.iv_music_play);
        ivMusicMenu = (ImageView) musicControl.findViewById(R.id.iv_music_menu);
        ivMusicPlay.setOnClickListener(this);
        musicControl.setOnClickListener(this);
        ivMusicMenu.setOnClickListener(this);
        musicName.setText(getPlayService().getMusicName());
        musicArtist.setText(getPlayService().getArtist());
        getPlayService().setOnPlayStateChangeLIstener(new PlayService.OnPlayStateChangeListener() {
            @Override
            public void onPlay() {
                ivMusicPlay.setImageResource(R.drawable.ic_bottom_play);
            }

            @Override
            public void onPause() {
                ivMusicPlay.setImageResource(R.drawable.ic_bottom_pause);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_music_control:
                start(PlayFragment.getInstance());
                break;
            case R.id.iv_music_play://暂停播放
                getPlayService().playMusic();
                break;
            case R.id.iv_music_menu://歌单
                break;
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

}
