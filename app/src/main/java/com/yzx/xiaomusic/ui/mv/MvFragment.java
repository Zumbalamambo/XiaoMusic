package com.yzx.xiaomusic.ui.mv;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.mvp.AbstractFragment;
import com.yzx.xiaomusic.common.mvp.factory.CreatePresenter;
import com.yzx.xiaomusic.utils.ToastUtils;

import static com.yzx.xiaomusic.ui.main.cloud.music.songsheetdetails.SongSheetDetailsFragment.KEY_MV_ID;

/**
 *
 * @author yzx
 * @date 2018/1/21
 * Description mv页面
 */
@CreatePresenter(MvPresenter.class)
public class MvFragment extends AbstractFragment<MvView,MvPresenter> implements MvView{


    private static MvFragment mvFragment;

    @SuppressLint("ValidFragment")
    private MvFragment() {
    }

    public static MvFragment getInstance(){
        if (mvFragment == null){
            mvFragment = new MvFragment();
        }
        return mvFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        String mvId = arguments.getString(KEY_MV_ID);
        if (TextUtils.isEmpty(mvId)){
            ToastUtils.showToast(R.string.error_data);
        }else {
            getMvData(mvId);
        }
        return getLayoutInflater().inflate(R.layout.fragment_mv,container,false);
    }

    @Override
    public void getMvData(String mvId) {
        getMvpPresenter().getMvData(mvId);
    }


//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_mv;
//    }
//
//    @Override
//    protected void initView(Bundle savedInstanceState) {
//
//    }

}
