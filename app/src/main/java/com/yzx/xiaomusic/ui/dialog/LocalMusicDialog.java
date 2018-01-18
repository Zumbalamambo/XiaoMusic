package com.yzx.xiaomusic.ui.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.ui.adapter.DialogLocalMusicAdapter;
import com.yzx.xiaomusic.ui.adapter.LocalMusicAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yzx on 2018/1/12.
 * Description
 */

public class LocalMusicDialog extends BottomSheetDialogFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        MusicInfo musicInfo = (MusicInfo) arguments.getSerializable(LocalMusicAdapter.MUSIC_INFO);
        View rootView = inflater.inflate(R.layout.dialog_music_local, container, false);
        bind = ButterKnife.bind(this, rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DialogLocalMusicAdapter adapter = new DialogLocalMusicAdapter();
        adapter.setDatas(musicInfo);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

}
