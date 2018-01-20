package com.yzx.xiaomusic.ui.main.cloud.music;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.ui.adapter.ChildCloudMusicAdapter;
import com.yzx.xiaomusic.ui.main.MainFragment;
import com.yzx.xiaomusic.ui.main.cloud.music.songsheetdetails.SongSheetDetailsFragment;
import com.yzx.xiaomusic.ui.main.cloud.music.songsheetlist.SongSheetListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yzx on 2018/1/12.
 * Description  cloudMusic----music
 */

public class ChildCloudFragment extends BaseFragment implements ChildCloudContract.View, ChildCloudMusicAdapter.OnItemClickLsitener {
    private static final String TAG = "yglChildCloudFragment";
    private static final String KEY_SONG_SHEET = "songSheet";
    private static ChildCloudFragment childCloudFragment;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ChildCloudPresenter mPresenter;
    private ChildCloudMusicAdapter songSheetAdapter;
    private ChildCloudMusicAdapter bannerAdapter;

    @SuppressLint("ValidFragment")
    private ChildCloudFragment() {
    }

    public static ChildCloudFragment getInstance(){
        if (childCloudFragment == null){
            childCloudFragment = new ChildCloudFragment();
        }
        return childCloudFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cloud_child;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mPresenter = new ChildCloudPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        创建RecyclerView & VirtualLayoutManager 对象并进行绑定
        //设置布局管理器
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getContext());
        recyclerView.setLayoutManager(virtualLayoutManager);
//        设置回收复用池大小
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
//        步骤3：设置Adapter
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        recyclerView.setAdapter(delegateAdapter);

        //创建存储adapter的list
        List<DelegateAdapter.Adapter> adapters = new ArrayList<>();

        //banner
        bannerAdapter = new ChildCloudMusicAdapter(new SingleLayoutHelper(), 1) {
            @Override
            public int getItemViewType(int position) {
                return ChildCloudMusicAdapter.TYPE_BANNER;
            }
        };
        adapters.add(bannerAdapter);

        ChildCloudMusicAdapter theFourAdapter = new ChildCloudMusicAdapter(new GridLayoutHelper(4), 4) {
            @Override
            public int getItemViewType(int position) {
                return ChildCloudMusicAdapter.TYPE_THE_FOUR;
            }
        };
        theFourAdapter.setOnItemClickListener(this);
        adapters.add(theFourAdapter);
        //歌单
        adapters.add(new ChildCloudMusicAdapter(new SingleLayoutHelper(),1));
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
        gridLayoutHelper.setHGap(5);//控制两个grid之间横向间距
        songSheetAdapter = new ChildCloudMusicAdapter(gridLayoutHelper, 6) {
            @Override
            public int getItemViewType(int position) {
                return ChildCloudMusicAdapter.TYPE_SONG_SHEET;
            }
        };
        songSheetAdapter.setOnItemClickListener(this);
        adapters.add(songSheetAdapter);
        delegateAdapter.setAdapters(adapters);

    }

    @Override
    public void loadData() {
        super.loadData();
        getSongSheetList("全部","hot",0,6,true);
    }

    @Override
    public void getSongSheetList(String cat, String order, int offset, int limit, boolean total) {
        mPresenter.getSongSheetDetails(cat,order,offset,limit,total);
    }

    @Override
    public void setDatas(SongSheet songSheet) {
        bannerAdapter.setDatas(songSheet);
        songSheetAdapter.setDatas(songSheet);
    }

    @Override
    public void onItemClickListener(View itemView, int position, SongSheet.PlaylistsBean playlistsBean, int type) {

                MainFragment parentFragment = (MainFragment) getParentFragment().getParentFragment();
        switch (type){
            case ChildCloudMusicAdapter.TYPE_SONG_SHEET:
                SongSheetDetailsFragment songSheetFragment = SongSheetDetailsFragment.getInstance();
                Bundle args =new Bundle();
                args.putSerializable(KEY_SONG_SHEET,playlistsBean);
                songSheetFragment.setArguments(args);
                parentFragment.start(songSheetFragment);
                break;
            case ChildCloudMusicAdapter.TYPE_THE_FOUR:
                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        parentFragment.start(SongSheetListFragment.getInstance());
                        break;
                    case 3:
                        break;
                }
                break;
        }
    }

}
