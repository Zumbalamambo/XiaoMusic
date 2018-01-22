package com.yzx.xiaomusic.ui.adapter;

import android.support.v7.widget.RecyclerView;

import com.yzx.xiaomusic.common.ItemClickListener;
import com.yzx.xiaomusic.common.OnItemClickLsitener;

/**
 * Created by yzx on 2018/1/21.
 * Description
 */

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements ItemClickListener{

    public OnItemClickLsitener onItemClickListener;

    @Override
    public void setOnItemClickListener(OnItemClickLsitener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
