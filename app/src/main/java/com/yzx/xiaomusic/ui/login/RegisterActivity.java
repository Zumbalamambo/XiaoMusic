package com.yzx.xiaomusic.ui.login;

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

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";
    @BindView(R.id.et_userName)
    TextInputEditText etUserName;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @OnClick({R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                showLoading();
                if (!TextUtils.isEmpty(getEditContent(etUserName))&&!TextUtils.isEmpty(getEditContent(etPassword))){

                }else {
                    showToast(R.string.notice_not_null, ToastUtils.TYPE_NOTICE);
                }
                break;
        }
    }

    public String getEditContent(EditText editText) {
        return editText.getText().toString().trim();
    }

}
