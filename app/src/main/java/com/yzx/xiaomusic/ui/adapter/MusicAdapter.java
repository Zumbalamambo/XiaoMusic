package com.yzx.xiaomusic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.utils.ResourceUtils;
import com.yzx.xiaomusic.utils.ScanMusicUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yzx on 2018/1/12.
 * Description 首页音乐Adapter
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.Holder> {


    private OnItemClickListener onItemClickListener;

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {

        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_music, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int i) {

        final Context context = holder.itemView.getContext();
        switch (i) {
            case 0:
                holder.tvName.setText(R.string.localMusic);
                holder.ivIcon.setImageResource(R.drawable.ic_local_music);
                List<MusicInfo> musicInfos = ScanMusicUtils.getLocalMusicInfoByPreference();
                if (musicInfos == null) {
                    holder.tvCount.setVisibility(View.GONE);
                } else {
                    holder.tvCount.setVisibility(View.VISIBLE);
                    holder.tvCount.setText(String.format(ResourceUtils.parseString(R.string.musicCount), musicInfos.size()));
                }
                break;
            case 1:
                holder.tvName.setText(R.string.recentPlay);
                holder.ivIcon.setImageResource(R.drawable.ic_recent_play);
                break;
            case 2:
                holder.tvName.setText(R.string.myCollection);
                holder.ivIcon.setImageResource(R.drawable.ic_my_collection);
                break;
            case 3:
                holder.ivIcon.setImageResource(R.drawable.ic_download_manager);
                holder.tvName.setText(R.string.downloadManager);
//                holder.ivIcon.setImageResource(R.drawable.ic_music_play_undownload_normal);
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onItemClickListener!=null){
                    onItemClickListener.onItemClickListener(holder.itemView,i);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public void setDatas() {
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_count)
        TextView tvCount;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener{
        void onItemClickListener(View itemView, int i);
    }
}
