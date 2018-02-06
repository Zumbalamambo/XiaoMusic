package com.yzx.xiaomusic.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.entities.ArtistCenterInfo;
import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.entities.SongSheetDetials;
import com.yzx.xiaomusic.service.PlayService;
import com.yzx.xiaomusic.service.PlayServiceManager;
import com.yzx.xiaomusic.utils.MusicDataUtils;
import com.yzx.xiaomusic.utils.ResourceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yzx on 2018/2/3.
 * Description
 */

public class MusicMenuAdapter extends RecyclerView.Adapter<MusicMenuAdapter.Holder> {

    private static final String TAG = "yglMusicMenuAdapter";
    private Context context;
    private List musicList;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new Holder(LayoutInflater.from(context).inflate(R.layout.item_music_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final Object musicInfo = musicList.get(position);
        holder.tvName.setText(MusicDataUtils.getMusicName(musicInfo));
        holder.tvArtist.setText(MusicDataUtils.getMusicArtist(musicInfo));

        if (position== PlayServiceManager.getInstance().getPlayService().getPlayListPosition()){
            int color = ResourceUtils.parseColor(R.color.colorAccent);
            holder.tvName.setTextColor(color);
            holder.tvArtist.setTextColor(color);
        }else {
            holder.tvName.setTextColor(ResourceUtils.parseColor(R.color.colorMusicNameGray));
            holder.tvArtist.setTextColor(ResourceUtils.parseColor(R.color.colorMusicArtistGray));
        }
//        Log.i(TAG, "onBindViewHolder: "+MusicDataUtils.getMusicName(musicInfo));
        final PlayService playService = PlayServiceManager.getInstance().getPlayService();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicInfo instanceof MusicInfo) {
                    if (MusicDataUtils.TYPE_LOCAL !=MusicDataUtils.getMusicType(playService.getMusicInfo())||playService.getPlayListPosition()!=position){
                        playService.setState(PlayService.STATE_IDLE);
                        playService.setPlayListPosition(position);
                    }
                } else if (musicInfo instanceof SongSheetDetials.ResultBean.TracksBean) {
                    if (MusicDataUtils.TYPE_SONG_SHEET !=MusicDataUtils.getMusicType(playService.getMusicInfo())||playService.getPlayListPosition()!=position){
                        playService.setState(PlayService.STATE_IDLE);
                        playService.setPlayListPosition(position);
                    }
                } else if (musicInfo instanceof ArtistCenterInfo.HotSongsBean) {
                    if (MusicDataUtils.TYPE_ARTIST_CENTER !=MusicDataUtils.getMusicType(playService.getMusicInfo())||playService.getPlayListPosition()!=position){
                        playService.setState(PlayService.STATE_IDLE);
                        playService.setPlayListPosition(position);
                    }
                }
                playService.playMusic();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
//        Log.i(TAG, "getItemCount: "+(musicList == null ? 0 : musicList.size()));
        return musicList == null ? 0 : musicList.size();
    }

    public void setMusicMenuDatas(List musicList) {
        this.musicList = musicList;
//        Log.i(TAG, "setMusicMenuDatas: ");
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_artist)
        TextView tvArtist;
        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
