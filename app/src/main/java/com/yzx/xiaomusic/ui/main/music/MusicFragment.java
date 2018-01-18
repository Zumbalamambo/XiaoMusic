package com.yzx.xiaomusic.ui.main.music;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.ui.adapter.MusicAdapter;

import butterknife.BindView;

/**
 * Created by yzx on 2018/1/12.
 * Description
 */

public class MusicFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MusicAdapter adapter;

    @Override
    public void fetchData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MusicAdapter();
        adapter.setDatas();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
