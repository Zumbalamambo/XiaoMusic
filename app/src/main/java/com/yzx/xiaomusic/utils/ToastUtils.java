package com.yzx.xiaomusic.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yzx.xiaomusic.MusicApplication;
import com.yzx.xiaomusic.R;


/**
 *
 * Created by yzx on 2017/12/4.
 */

public class ToastUtils {


    private static Toast toast;
    public static final int TYPE_SUCCESS =1;
    public static final int TYPE_FAIL =2;
    public static final int TYPE_NOTICE =3;
    public static final int TYPE_DEFALUT =4;

    public static void showToast (int msg) {
        showToast(msg,TYPE_DEFALUT);
    }
    public static void showToast (String msg) {
        showToast(msg,TYPE_DEFALUT);
    }
    public static void showToast (String msg, int type) {
        showToast(msg,type, Toast.LENGTH_SHORT);
    }
    public static void showToast (int msg,int type) {
        showToast(msg,type, Toast.LENGTH_SHORT);
    }

    public static void showToast(int msg,int type,int duration){
        showToast(ResourceUtils.parseString(msg),type,duration);
    }

    public static void showToast(String msg, int type, int duration){
        if (toast==null){
            toast = Toast.makeText(MusicApplication.getApplication(), msg, Toast.LENGTH_SHORT);
        }

        View toastView = LayoutInflater.from(MusicApplication.getApplication()).inflate(R.layout.layout_toast, null);
        ImageView ivToastType = (ImageView) toastView.findViewById(R.id.iv_toast_type);
        TextView ivToastMsg = (TextView) toastView.findViewById(R.id.iv_toast_msg);
        ivToastMsg.setText(msg);
        switch (type){
            case TYPE_SUCCESS://成功
                ivToastType.setVisibility(View.VISIBLE);
                ivToastType.setImageResource(R.drawable.ic_successful);
                toast.setView(toastView);
                break;
            case TYPE_FAIL://失败
                ivToastType.setVisibility(View.VISIBLE);
                ivToastType.setImageResource(R.drawable.ic_error);
                toast.setView(toastView);
                break;
            case TYPE_NOTICE://提示
                ivToastType.setVisibility(View.VISIBLE);
                ivToastType.setImageResource(R.drawable.ic_notice);
                toast.setView(toastView);
                break;
            case TYPE_DEFALUT://默认
                ivToastType.setVisibility(View.GONE);
                toast.setView(toastView);
                break;
        }

        switch (duration){
            case Toast.LENGTH_SHORT:
                toast.setDuration(Toast.LENGTH_SHORT);
                break;
            case Toast.LENGTH_LONG:
                toast.setDuration(Toast.LENGTH_LONG);
                break;
        }

        toast.show();
    }

}
