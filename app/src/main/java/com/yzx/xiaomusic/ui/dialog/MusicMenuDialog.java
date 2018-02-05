package com.yzx.xiaomusic.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.service.PlayServiceManager;
import com.yzx.xiaomusic.ui.adapter.MusicMenuAdapter;
import com.yzx.xiaomusic.utils.MusicDataUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yzx on 2018/2/3.
 * Description
 */

public class MusicMenuDialog extends BottomSheetDialogFragment {

    private static final String TAG = "yglMusicMenuDialog";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_menu_music, container, false);
        bind = ButterKnife.bind(this, rootView);
        PlayServiceManager playServiceManager = PlayServiceManager.getInstance();
        List musicList = null;
        switch (MusicDataUtils.getMusicType(playServiceManager.getPlayService().getMusicInfo())) {
            case MusicDataUtils.TYPE_LOCAL:
                musicList = playServiceManager.getLocalMusicList();
                break;
            case MusicDataUtils.TYPE_SONG_SHEET:
                musicList = playServiceManager.getSongSheetMusicList();
                break;
            case MusicDataUtils.TYPE_ARTIST_CENTER:
                musicList = playServiceManager.getHotSongs();
                break;
            default:
                break;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MusicMenuAdapter adapter = new MusicMenuAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setMusicMenuDatas(musicList);
        recyclerView.scrollToPosition(PlayServiceManager.getInstance().getPlayService().getPlayListPosition());
        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        dialog.setTitle("我是标题");
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        int dialogHeight = (int) (getContext().getResources().getDisplayMetrics().heightPixels * 0.5);
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, dialogHeight);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) getDialog();
        View view = bottomSheetDialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet);
        BottomSheetBehavior.from(view).setPeekHeight(1600);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
