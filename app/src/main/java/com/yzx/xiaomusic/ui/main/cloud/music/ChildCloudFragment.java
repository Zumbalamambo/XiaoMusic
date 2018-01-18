package com.yzx.xiaomusic.ui.main.cloud.music;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.network.AppHttpClient;
import com.yzx.xiaomusic.network.api.MuiscApi;
import com.yzx.xiaomusic.ui.adapter.ChildMusicAdapter;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yzx on 2018/1/12.
 * Description
 */

public class ChildCloudFragment extends BaseFragment {
    private static final String TAG = "yglChildCloudFragment";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cloud_child;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
//        创建RecyclerView & VirtualLayoutManager 对象并进行绑定
        VirtualLayoutManager layoutManager=new VirtualLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
//        设置回收复用池大小
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
//        步骤3：设置Adapter
        final ChildMusicAdapter adapter = new ChildMusicAdapter(layoutManager);

        List<LayoutHelper> helpers = new LinkedList<>();
        //添加单行
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setItemCount(1);
        helpers.add(singleLayoutHelper);

        //添加grid
        GridLayoutHelper gridLayoutHelper=new GridLayoutHelper(3);
//        gridLayoutHelper.setPaddingRight((int) DensityUtils.dip2px(4));
//        gridLayoutHelper.setPaddingLeft((int) DensityUtils.dip2px(4));

        gridLayoutHelper.setItemCount(50);
        helpers.add(gridLayoutHelper);

        adapter.setLayoutHelpers(helpers);
        recyclerView.setAdapter(adapter);
        AppHttpClient
                .getInstance()
                .getService(MuiscApi.class)
                .getSongSheet("全部","hot",0,false,50)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MvpObserver<SongSheet>() {
                    @Override
                    protected void onSuccess(SongSheet songSheet) {
                        Log.i(TAG, "onSuccess: ");
                        adapter.setDatas(songSheet);
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        super.onFail(errorMsg);
                    }
                });

    }
    @Override
    public void fetchData() {

    }
}
