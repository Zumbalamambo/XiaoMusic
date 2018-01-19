package com.yzx.xiaomusic.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.ui.main.cloud.music.songsheet.SongSheetActivity;
import com.yzx.xiaomusic.utils.GlideUtils;
import com.yzx.xiaomusic.utils.ResourceUtils;

import java.util.List;

/**
 * Created by yzx on 2018/1/17.
 * Description
 */

public class ChildMusicAdapter extends VirtualLayoutAdapter<ChildMusicAdapter.Holder> {


    private static final String TAG = "yglChildMusicAdapter";
    private static final int TYPE_TITLE = 1;
    private static final int TYPE_SONG_SHEET = 2;
    public static final String KEY_SONG_SHEET = "songSheet";
    private SongSheet songSheet;


    public ChildMusicAdapter(@NonNull VirtualLayoutManager layoutManager) {
        super(layoutManager);
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return TYPE_TITLE;
        }else if (position<52){
            return TYPE_SONG_SHEET;
        }else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i){
            case TYPE_TITLE:
                return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_music_child_tite, viewGroup, false));
            case TYPE_SONG_SHEET:
                return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_music_child_song_sheet, viewGroup, false));
                default:
            return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_music_child, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, int i) {
        final Context context = holder.itemView.getContext();
        switch (getItemViewType(i)){
            case TYPE_TITLE:
//                tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                break;
            case TYPE_SONG_SHEET:

                if (songSheet!=null){
                    final List<SongSheet.PlaylistsBean> playlists = songSheet.getPlaylists();
                    final SongSheet.PlaylistsBean playlistsBean = playlists.get(i-1);
                    holder.tvSongSheetDes.setText(playlistsBean.getName());
                    if (playlistsBean.getPlayCount()>100000){
                        holder.tvSongSheetNum.setText(String.format("%s%s",
                                String.valueOf(playlistsBean.getPlayCount()/10000),
                                ResourceUtils.parseString(R.string.TenThousand)));
                    }else {
                        holder.tvSongSheetNum.setText(String.valueOf(playlistsBean.getPlayCount()));
                    }

                    GlideUtils.loadImg(context,playlistsBean.getCoverImgUrl(),holder.ivSongSheetBg);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, SongSheetActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(KEY_SONG_SHEET,playlistsBean);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    });
                }

                break;
        }
    }

    @Override
    public int getItemCount() {

        int itemCount =0;
        for (int i = 0; i < getLayoutHelpers().size(); i++) {
            itemCount += getLayoutHelpers().get(i).getItemCount();
        }
        return itemCount;
    }

    public void setDatas(SongSheet songSheet) {
        this.songSheet = songSheet;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView tvSongSheetDes;
        private TextView tvTitle;
        private TextView tvSongSheetNum;
        private ImageView ivSongSheetBg;

        public Holder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvSongSheetDes = (TextView) itemView.findViewById(R.id.tv_songSheetDes);
            ivSongSheetBg = (ImageView) itemView.findViewById(R.id.iv_songSheetBg);
            tvSongSheetNum = (TextView) itemView.findViewById(R.id.tv_songSheetNum);

        }
    }
}
