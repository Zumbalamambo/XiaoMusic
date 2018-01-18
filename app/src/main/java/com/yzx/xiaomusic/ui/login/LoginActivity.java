package com.yzx.xiaomusic.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseActivity;
import com.yzx.xiaomusic.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author yzx
 * @date 2018/1/11
 * Description
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.et_userName)
    TextInputEditText etUserName;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                showLoading();
                if (!TextUtils.isEmpty(getEditContent(etUserName))&&!TextUtils.isEmpty(getEditContent(etPassword))){

                }else {
                    ToastUtils.showToast(R.string.notice_not_null, ToastUtils.TYPE_NOTICE);
                }
                break;
            case R.id.tv_register:
                startActivity(new Intent(context, RegisterActivity.class));
                break;
        }
    }

    public String getEditContent(EditText editText) {
        return editText.getText().toString().trim();
    }

}
