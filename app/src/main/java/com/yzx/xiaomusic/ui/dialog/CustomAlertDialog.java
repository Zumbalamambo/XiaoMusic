package com.yzx.xiaomusic.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.yzx.xiaomusic.R;

/**
 * Created by yzx on 2018/2/22.
 * Description
 */

public class CustomAlertDialog extends DialogFragment {

    private String title;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        TextView titleView = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.dialog_custom_title, null);
        builder.setCustomTitle(titleView);
        titleView.setText(title);
        return builder.create();
    }

    public void setTitle(String title){
        this.title = title;
        notify();
    }
}
