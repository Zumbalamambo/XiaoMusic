package com.yzx.xiaomusic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.utils.GlideUtils;
import com.yzx.xiaomusic.utils.ResourceUtils;

/**
 * Created by yzx on 2018/1/20.
 * Description
 */

public class SongSheetAdapter extends RecyclerView.Adapter<SongSheetAdapter.Holder> {

    private OnItemClickLsitener onItemClickListener;
    private SongSheet songSheet;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music_child_song_sheet, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        if (songSheet != null) {
            final SongSheet.PlaylistsBean playlistsBean = songSheet.getPlaylists().get(position);
            holder.tvSongSheetDes.setText(playlistsBean.getName());
            if (playlistsBean.getPlayCount()>100000){
                holder.tvSongSheetNum.setText(String.format("%s%s",
                        String.valueOf(playlistsBean.getPlayCount()/10000),
                        ResourceUtils.parseString(R.string.TenThousand)));
            }else {
                holder.tvSongSheetNum.setText(String.valueOf(playlistsBean.getPlayCount()));
            }
            final Context context = holder.itemView.getContext();
            GlideUtils.loadImg(context,playlistsBean.getCoverImgUrl(),holder.ivSongSheetBg);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){

                        onItemClickListener.onItemClickListener(holder.itemView,position,playlistsBean);
                    }
                }
            });
        }
    }

    public void setDatas(SongSheet songSheet) {
        this.songSheet = songSheet;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickLsitener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return (songSheet==null||songSheet.getPlaylists()==null)?0:songSheet.getPlaylists().size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView tvSongSheetDes;
        private TextView tvSongSheetNum;
        private ImageView ivSongSheetBg;

        public Holder(View itemView) {
            super(itemView);

            tvSongSheetDes = (TextView) itemView.findViewById(R.id.tv_songSheetDes);
            ivSongSheetBg = (ImageView) itemView.findViewById(R.id.iv_songSheetBg);
            tvSongSheetNum = (TextView) itemView.findViewById(R.id.tv_songSheetNum);

        }
    }

    public interface OnItemClickLsitener{
        void onItemClickListener(View itemView, int position, SongSheet.PlaylistsBean playlistsBean);
    }
}
