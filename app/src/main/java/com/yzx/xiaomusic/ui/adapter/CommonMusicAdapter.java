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
import com.yzx.xiaomusic.common.Constants;
import com.yzx.xiaomusic.entities.CommonMusicInfo;
import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.entities.SongSheetDetials;
import com.yzx.xiaomusic.service.PlayService;
import com.yzx.xiaomusic.service.PlayServiceManager;
import com.yzx.xiaomusic.ui.dialog.CloudMusicDialog;
import com.yzx.xiaomusic.ui.dialog.LocalMusicDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yzx.xiaomusic.ui.main.music.local.LocalMusicFragment.MUSIC_INFO;


/**
 * Created by yzx on 2018/1/11.
 * Description 音乐列表共同Adapter
 */

public class CommonMusicAdapter extends BaseAdapter<CommonMusicAdapter.Holder> {

    public static final int DATA_TYPE_LOCAL_MUSIC = 1;//本地音乐类型
    public static final int DATA_TYPE_SONG_SHEET_MUSIC = 2;//歌单音乐类型

    private static final String TAG = "yglCommonMusicAdapter";
    private List<SongSheetDetials.ResultBean.TracksBean> songSheetData;
    private Context context;
    private List<MusicInfo> localMusicInfo;
    private int type;

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_music_common, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int i) {

        switch (type){
            case DATA_TYPE_LOCAL_MUSIC:
                final MusicInfo musicInfo = localMusicInfo.get(i);
                holder.tvSerialNumber.setVisibility(View.GONE);
                holder.ivMv.setVisibility(View.GONE);
                holder.tvName.setText(musicInfo.getName());
                holder.tvArtist.setText(musicInfo.getArtist());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlayServiceManager.getInstance().setLocalMusicList(localMusicInfo);
                        PlayServiceManager.getInstance().getPlayService().setMusicType(PlayService.TYPE_LOCAL);
                        PlayServiceManager.getInstance().getPlayService().setPlayListPosition(i);
                        PlayServiceManager.getInstance()
                                .setCommonMusicInfo(
                                        new CommonMusicInfo(null,musicInfo.getMd5(),musicInfo.getName(),musicInfo.getArtist(),
                                        musicInfo.poster,PlayService.STATE_IDLE, Constants.TYPE_MUSIC_LOCAL,i,0, musicInfo.getDuration(),
                                        localMusicInfo,null));
                        if (onItemClickListener!=null){
                            onItemClickListener.onItemClickListener(holder.itemView,i,musicInfo,DATA_TYPE_LOCAL_MUSIC);
                        }
                    }
                });
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
                break;
            case DATA_TYPE_SONG_SHEET_MUSIC:
                final SongSheetDetials.ResultBean.TracksBean bean = songSheetData.get(i);
                holder.tvName.setText(bean.getName());
                holder.tvSerialNumber.setText(String.valueOf(i+1));
                holder.ivMv.setVisibility(bean.getMvid()>0 ? View.VISIBLE:View.GONE);
                holder.tvArtist.setText(bean.getArtists().get(0).getName());
                holder.ivMv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener!=null){
                            onItemClickListener.onItemClickListener(holder.ivMv,i,bean,DATA_TYPE_SONG_SHEET_MUSIC);
                        }
                    }
                });
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
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlayServiceManager.getInstance().setSongSheetMusicList(songSheetData);
                        PlayServiceManager.getInstance().getPlayService().setMusicType(PlayService.TYPE_NET);
                        PlayServiceManager.getInstance().getPlayService().setPlayListPosition(i);
                        if (onItemClickListener!=null){
                            onItemClickListener.onItemClickListener(holder.itemView,i,bean,DATA_TYPE_SONG_SHEET_MUSIC);
                        }
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        switch (type){
            case DATA_TYPE_LOCAL_MUSIC:
                return localMusicInfo ==null?0: localMusicInfo.size();
            case DATA_TYPE_SONG_SHEET_MUSIC:
                return songSheetData ==null?0: songSheetData.size();
                default:
                    return 0;
        }
    }

    public void setDatas(int type,Object data) {
        this.type = type;
        switch (type){
            case DATA_TYPE_LOCAL_MUSIC:
                this.localMusicInfo = (List<MusicInfo>) data;
                break;
            case DATA_TYPE_SONG_SHEET_MUSIC:
                this.songSheetData =(List<SongSheetDetials.ResultBean.TracksBean>)data;
                break;
        }
        notifyDataSetChanged();
    }

//    @Override
//    public void onClick(View v) {
//        onItemClickListener.onItemClickListener(v,0,null,1);
//    }

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
            ButterKnife.bind(this,itemView);
        }
    }

}
