package com.yzx.xiaomusic.ui.main.cloud.music.songsheetlist;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.ui.adapter.ChildCloudMusicAdapter;
import com.yzx.xiaomusic.ui.adapter.SongSheetAdapter;
import com.yzx.xiaomusic.ui.main.MainFragment;
import com.yzx.xiaomusic.ui.main.cloud.music.songsheetdetails.SongSheetDetailsFragment;
import com.yzx.xiaomusic.utils.DensityUtils;

import butterknife.BindView;

/**
 * Created by yzx on 2018/1/12.
 * Description  歌单列表
 */

public class SongSheetListFragment extends BaseFragment implements SongSheetListContract.View, ChildCloudMusicAdapter.OnItemClickLsitener {
    private static final String TAG = "yglSongSheetListFragment";
    private static final String KEY_SONG_SHEET = "songSheet";
    private static SongSheetListFragment childCloudFragment;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private SongSheetListPresenter mPresenter;
    private SongSheetAdapter adapter;

    @SuppressLint("ValidFragment")
    private SongSheetListFragment() {
    }

    public static SongSheetListFragment getInstance(){
        if (childCloudFragment == null){
            childCloudFragment = new SongSheetListFragment();
        }
        return childCloudFragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cloud_song_sheet;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mPresenter = new SongSheetListPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int offset = (int)DensityUtils.dip2px(3);
                outRect.set(offset,0,offset,0);
            }
        });
        adapter = new SongSheetAdapter();
        adapter.setOnItemClickListener(new SongSheetAdapter.OnItemClickLsitener() {
            @Override
            public void onItemClickListener(View itemView, int position, SongSheet.PlaylistsBean playlistsBean) {
//                MainFragment parentFragment = (MainFragment) getParentFragment().getParentFragment().getParentFragment();
                SongSheetDetailsFragment songSheetFragment = SongSheetDetailsFragment.getInstance();
                Bundle args =new Bundle();
                args.putSerializable(KEY_SONG_SHEET,playlistsBean);
                songSheetFragment.setArguments(args);
                start(songSheetFragment);
            }
        });
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        getSongSheetList("全部","hot",0,10,true);
    }

    @Override
    public void getSongSheetList(String cat, String order, int offset, int limit, boolean total) {
        mPresenter.getSongSheetDetails(cat,order,offset,limit,total);
    }

    @Override
    public void setDatas(SongSheet songSheet) {
        adapter.setDatas(songSheet);
    }

    @Override
    public void onItemClickListener(View itemView, int position, SongSheet.PlaylistsBean playlistsBean, int type) {

            MainFragment parentFragment = (MainFragment) getParentFragment().getParentFragment();
            SongSheetDetailsFragment songSheetFragment = SongSheetDetailsFragment.getInstance();
            Bundle args =new Bundle();
            args.putSerializable(KEY_SONG_SHEET,playlistsBean);
            songSheetFragment.setArguments(args);
            parentFragment.start(songSheetFragment);
    }
}
