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
     * @param type    类型
     */
        void onItemClickListener(View itemView, int position, Object data, int type);
    }