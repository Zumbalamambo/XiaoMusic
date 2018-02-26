package com.yzx.xiaomusic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.db.entity.SongSheet;
import com.yzx.xiaomusic.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yzx on 2018/2/26.
 * Description
 */

public class CollectedSongSheetAdapter extends RecyclerView.Adapter<CollectedSongSheetAdapter.Holder> {


    private List<SongSheet> songSheets;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collected_song_sheet, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Context context = holder.itemView.getContext();
        SongSheet songSheet = songSheets.get(position);
        GlideUtils.loadImg(context,songSheet.getCoverUrl(),GlideUtils.TYPE_DEFAULT,holder.ivSongSheetBg);
        holder.tvTitle.setText(songSheet.getTitle());
        holder.tvSubtitle.setText("by "+songSheet.getAuthor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return songSheets == null ? 0 : songSheets.size();
    }

    public void setData(List<SongSheet> songSheets) {
        this.songSheets = songSheets;
        notifyDataSetChanged();
    }


    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_songSheetBg)
        ImageView ivSongSheetBg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_subtitle)
        TextView tvSubtitle;
        public Holder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
