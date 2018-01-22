package com.yzx.xiaomusic.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.ui.dialog.LocalMusicDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yzx on 2018/1/11.
 * Description
 */

public class LocalMusicAdapter extends RecyclerView.Adapter<LocalMusicAdapter.Holder> {



    private List<MusicInfo> musicInfoList;
    private Context context;
    public static final String MUSIC_INFO = "musicInfo";

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_music_common, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int i) {
        final MusicInfo musicInfo = musicInfoList.get(i);
        holder.tvSerialNumber.setVisibility(View.GONE);
        holder.ivMv.setVisibility(View.GONE);
        holder.tvName.setText(musicInfo.getName());
        holder.tvArtist.setText(musicInfo.getArtist());
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalMusicDialog dialog = new LocalMusicDialog();
                Bundle args = new Bundle();
                args.putSerializable(MUSIC_INFO, musicInfo);
                dialog.setArguments(args);
                dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "local");
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicInfoList == null ? 0 : musicInfoList.size();
    }

    public void setDatas(List<MusicInfo> musicInfoList) {
        this.musicInfoList = musicInfoList;
        notifyDataSetChanged();
    }


    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_SerialNumber)
        TextView tvSerialNumber;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_artist)
        TextView tvArtist;
        @BindView(R.id.iv_mv)
        ImageView ivMv;
        @BindView(R.id.iv_more)
        ImageView ivMore;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
