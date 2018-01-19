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
import com.yzx.xiaomusic.entities.SongSheetDetials;
import com.yzx.xiaomusic.ui.dialog.CloudMusicDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yzx.xiaomusic.ui.main.music.local.LocalMusicActivity.MUSIC_INFO;


/**
 * Created by yzx on 2018/1/11.
 * Description
 */

public class CloudMusicAdapter extends RecyclerView.Adapter<CloudMusicAdapter.Holder> {


    private static final String TAG = "yglCloudMusicAdapter";
    private List<SongSheetDetials.ResultBean.TracksBean> musicInfoList;
    private Context context;

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_music_local, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int i) {
        final SongSheetDetials.ResultBean.TracksBean bean = musicInfoList.get(i);
        holder.tvName.setText(bean.getName());
        holder.tvSerialNumber.setText(String.valueOf(i+1));
//        Log.i(TAG, "onBindViewHolder: "+bean.getArtists().get(0).getName());
        holder.tvArtist.setText(bean.getArtists().get(0).getName());
        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloudMusicDialog dialog=new CloudMusicDialog();
                Bundle args =new Bundle();
                args.putSerializable(MUSIC_INFO,bean);
                dialog.setArguments(args);
                dialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"cloud");
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicInfoList==null?0:musicInfoList.size();
    }

    public void setDatas(List<SongSheetDetials.ResultBean.TracksBean> musicInfoList) {
        this.musicInfoList = musicInfoList;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_more)
        ImageView ivMore;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_artist)
        TextView tvArtist;
        @BindView(R.id.tv_SerialNumber)
        TextView tvSerialNumber;
        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
