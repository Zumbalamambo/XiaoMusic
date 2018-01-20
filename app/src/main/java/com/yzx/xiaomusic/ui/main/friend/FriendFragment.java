package com.yzx.xiaomusic.ui.main.friend;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;


/**
 * Created by yzx on 2018/1/12.
 * Description
 */

public class FriendFragment extends BaseFragment {

    private static FriendFragment friendFragment;

    @SuppressLint("ValidFragment")
    private FriendFragment() {
    }

    public static FriendFragment getInstance(){
        if (friendFragment == null){
            friendFragment = new FriendFragment();
        }
        return friendFragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_friend;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
