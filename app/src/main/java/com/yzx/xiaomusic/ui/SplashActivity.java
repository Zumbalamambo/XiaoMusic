package com.yzx.xiaomusic.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseActivity;
import com.yzx.xiaomusic.ui.main.MainActivity;
import com.yzx.xiaomusic.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by yzx on 2018/1/10.
 * Description
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.iv_splash)
    ImageView ivSplash;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

//        GlideUtils.loadImg(this, R.drawable.img1, GlideUtils.TRANSFORM_BLUR,ivSplash);

        Glide.with(context).load(R.drawable.splash_girl).into(ivSplash);
        RxPermissions rxPermissions=new RxPermissions(this);
        rxPermissions.setLogging(true);
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                        toMainActivity();
                    } else {
                        // At least one permission is denied
                        showToast(R.string.notice_permission, ToastUtils.TYPE_NOTICE);
                        toMainActivity();
                    }
                    }
                });
    }

    private void toMainActivity() {
        Observable
                .timer(5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    }
                });
    }
}
