package com.yzx.xiaomusic.ui.main.cloud.music;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.ui.adapter.ChildCloudMusicAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yzx on 2018/1/12.
 * Description
 */

public class ChildCloudFragment extends BaseFragment implements ChildCloudContract.View{
    private static final String TAG = "yglChildCloudFragment";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ChildCloudPresenter mPresenter;
    private ChildCloudMusicAdapter songSheetAdapter;
    private ChildCloudMusicAdapter bannerAdapter;

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

        adapters.add(new ChildCloudMusicAdapter(new GridLayoutHelper(4),4){
            @Override
            public int getItemViewType(int position) {
                return ChildCloudMusicAdapter.TYPE_THE_FOUR;
            }
        });
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
        adapters.add(songSheetAdapter);
        delegateAdapter.setAdapters(adapters);

    }
    @Override
    public void fetchData() {
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
}
