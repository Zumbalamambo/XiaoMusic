package com.yzx.xiaomusic.common;

import android.view.View;

/**
 * 条目点击接口
 * @author yzx
 */
public interface OnItemClickLsitener{
    /**
     *
     * @param itemView
     * @param position
     * @param data   需要的数据
     */
        void onItemClickListener(View itemView, int position, Object data);
    }