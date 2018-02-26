package com.yzx.xiaomusic.ui.main.music;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.ui.adapter.MusicAdapter;
import com.yzx.xiaomusic.ui.main.MainFragment;
import com.yzx.xiaomusic.ui.main.music.local.LocalMusicFragment;

import butterknife.BindView;

/**
 * Created by yzx on 2018/1/12.
 * Description
 */

public class MusicFragment extends BaseFragment {
    private static MusicFragment musicFragment;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MusicAdapter adapter;

    @SuppressLint("ValidFragment")
    private MusicFragment() {
    }

    public static MusicFragment getInstance(){
        if (musicFragment == null){
            musicFragment = new MusicFragment();
        }
        return musicFragment;
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
        adapter.setOnItemClickListener(new MusicAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View itemView, int i) {
                 switch (i) {
                    case 0:
                            MainFragment parentFragment = (MainFragment) getParentFragment();
                            parentFragment.start(LocalMusicFragment.getInstance());
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                        default:
                            break;
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }
}
