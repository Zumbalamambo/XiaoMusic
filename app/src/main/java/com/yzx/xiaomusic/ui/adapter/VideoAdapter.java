package com.yzx.xiaomusic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.ItemClickListener;
import com.yzx.xiaomusic.common.OnItemClickLsitener;
import com.yzx.xiaomusic.entities.MvData;
import com.yzx.xiaomusic.entities.VideoList;
import com.yzx.xiaomusic.utils.GlideUtils;
import com.yzx.xiaomusic.utils.SmallVideoHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yzx on 2018/1/30.
 * Description 视频Adapter
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.Holder> implements ItemClickListener {

    public static final String TAG = "VideoAdapter";
    private List<VideoList.DataBean> data;
    private Context context;
    private OnItemClickLsitener onItemClickListener;
    private SmallVideoHelper smallVideoHelper;
    private SmallVideoHelper.GSYSmallVideoHelperBuilder smallVideoHelperBuilder;


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_video, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        final VideoList.DataBean dataBean = data.get(position);
        holder.tvTitle.setText(dataBean.getName());
        GlideUtils.loadImg(context, dataBean.getCover(), holder.ivCover);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(holder.itemView, position, dataBean, -1);
                }
            }
        });
        smallVideoHelper.addVideoPlayer(position, holder.ivCover, TAG, holder.rlVideoContainer, holder.ivPlayPause);
//        smallVideoHelper.setPlayPositionAndTag(position, TAG);
        holder.ivPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    notifyDataSetChanged();
                    smallVideoHelper.setPlayPositionAndTag(position, TAG);
                    holder.ivPlayPause.setVisibility(View.INVISIBLE);
                    onItemClickListener.onItemClickListener(holder.ivCover, position, dataBean, -1);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setVideoData(List<VideoList.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addVideoData(List<VideoList.DataBean> data) {
        if (this.data == null) {
            this.data = data;
        } else {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public void setOnItemClickListener(OnItemClickLsitener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 播放视频
     * @param mvData
     */
    public void playVideo(MvData mvData) {
        smallVideoHelperBuilder.setVideoTitle(mvData.getData().getName()).setUrl(mvData.getData().getBrs().get_$720());

        smallVideoHelper.startPlay();
    }

    public void setVideoHelper(SmallVideoHelper smallVideoHelper, SmallVideoHelper.GSYSmallVideoHelperBuilder smallVideoHelperBuilder) {
        this.smallVideoHelper = smallVideoHelper;
        this.smallVideoHelperBuilder = smallVideoHelperBuilder;
    }


    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.fl_videoContainer)
        FrameLayout flVideoContainer;
        @BindView(R.id.rl_videoContainer)
        RelativeLayout rlVideoContainer;
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_more)
        ImageView ivMore;
        @BindView(R.id.iv_play_pause)
        ImageView ivPlayPause;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
