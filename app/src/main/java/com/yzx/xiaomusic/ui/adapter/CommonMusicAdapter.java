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
import com.yzx.xiaomusic.entities.ArtistCenterInfo;
import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.entities.SearchResult;
import com.yzx.xiaomusic.entities.SongSheetDetials;
import com.yzx.xiaomusic.service.MusicAddressModel;
import com.yzx.xiaomusic.service.PlayService;
import com.yzx.xiaomusic.service.PlayServiceManager;
import com.yzx.xiaomusic.ui.dialog.CloudMusicDialog;
import com.yzx.xiaomusic.ui.dialog.LocalMusicDialog;
import com.yzx.xiaomusic.utils.MusicDataUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yzx.xiaomusic.ui.main.music.local.LocalMusicFragment.MUSIC_INFO;


/**
 * Created by yzx on 2018/1/11.
 * Description 音乐列表共同Adapter
 */

public class CommonMusicAdapter extends BaseAdapter<CommonMusicAdapter.Holder>{

    private static final String TAG = "yglCommonMusicAdapter";
    private Context context;
    private Object data;

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_music_common, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int i) {

        if (data instanceof SongSheetDetials){//歌单数据
            final List<SongSheetDetials.ResultBean.TracksBean> songSheetInfo = ((SongSheetDetials) data).getResult().getTracks();
            final SongSheetDetials.ResultBean.TracksBean songSheetMusicInfo = songSheetInfo.get(i);
            holder.tvName.setText(songSheetMusicInfo.getName());
            holder.tvSerialNumber.setText(String.valueOf(i+1));
            //文件是否存在
            showIsMusicExsitIcon(holder.ivIsDownloaded, songSheetMusicInfo.getName(),songSheetMusicInfo.getId());

            holder.ivMv.setVisibility(songSheetMusicInfo.getMvid()>0 ? View.VISIBLE:View.GONE);
            holder.tvArtist.setText(songSheetMusicInfo.getArtists().get(0).getName());
            holder.ivMv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClickListener(holder.ivMv,i,songSheetMusicInfo);
                    }
                }
            });
            holder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CloudMusicDialog dialog=new CloudMusicDialog();
                    Bundle args =new Bundle();
                    args.putSerializable(MUSIC_INFO,songSheetMusicInfo);
                    dialog.setArguments(args);
                    dialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"cloud");
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayServiceManager.getInstance().setSongSheetMusicList(songSheetInfo);
                    PlayService playService = PlayServiceManager.getInstance().getPlayService();
                    playService.setMusicInfo(songSheetMusicInfo);
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClickListener(holder.itemView,i,songSheetMusicInfo);
                    }
                }
            });
        }else if (data instanceof ArtistCenterInfo){//歌手中心
            final List<ArtistCenterInfo.HotSongsBean> hotSongs = ((ArtistCenterInfo) data).getHotSongs();
            final ArtistCenterInfo.HotSongsBean hotSongsBean = hotSongs.get(i);
            holder.tvName.setText(hotSongsBean.getName());
            holder.tvSerialNumber.setText(String.valueOf(i+1));
            holder.ivMv.setVisibility(hotSongsBean.getMv()>0 ? View.VISIBLE:View.GONE);
            holder.tvArtist.setText(hotSongsBean.getAr().get(0).getName());
            //文件是否存在
            showIsMusicExsitIcon(holder.ivIsDownloaded, hotSongsBean.getName(), hotSongsBean.getId());

            holder.ivMv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClickListener(holder.ivMv,i,hotSongsBean);
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayServiceManager.getInstance().setArtistCenterMusicList(hotSongs);
                    PlayService playService = PlayServiceManager.getInstance().getPlayService();
                    playService.setMusicInfo(hotSongsBean);
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClickListener(holder.itemView,i,hotSongsBean);
                    }
                }
            });
        }else if (data instanceof SearchResult){//搜索
            final SearchResult.ResultBean.SongsBean songsBean = ((SearchResult) data).getResult().getSongs().get(i);
            holder.tvSerialNumber.setVisibility(View.GONE);
            holder.ivMv.setVisibility(View.GONE);
            holder.tvName.setText(songsBean.getName());
            //文件是否存在
//            showIsMusicExsitIcon(holder.ivIsDownloaded, songsBean.getName(), songsBean.getId());
            holder.ivIsDownloaded.setVisibility(View.GONE);
            holder.tvArtist.setText(songsBean.getArtists().get(0).getName());
            holder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    showDownloadDialog(songsBean);
                    downLoadMusic(songsBean.getName(), songsBean.getId());
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayServiceManager.getInstance().getPlayService().playNetMusic(songsBean.getName(), String.valueOf(songsBean.getId()));
                }
            });
        }else {//本地音乐

            final List<MusicInfo> localMusicInfo = (List<MusicInfo>)data;
            final MusicInfo musicInfo = localMusicInfo.get(i);
            holder.tvSerialNumber.setVisibility(View.GONE);
            holder.ivMv.setVisibility(View.GONE);
            holder.tvName.setText(musicInfo.getName());
            holder.tvArtist.setText(musicInfo.getArtist());
            holder.ivIsDownloaded.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayServiceManager.getInstance().setLocalMusicList(localMusicInfo);
//                    PlayServiceManager.getInstance().getPlayService().setPlayListPosition(i);
                    PlayServiceManager.getInstance().getPlayService().setMusicInfo(musicInfo);
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClickListener(holder.itemView,i,musicInfo);
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
        }
    }

    /**
     * 下载音乐
     * @param name
     * @param id
     */
    private void downLoadMusic(String name, int id) {
        MusicAddressModel.getInstance().downloadMusic(context,name, String.valueOf(id));
    }

    private void showIsMusicExsitIcon(ImageView view, String name, int id) {
        File file = new File(MusicDataUtils.getMusicPath(name, String.valueOf(id)));
        if (file.exists()){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {

        if (data instanceof SongSheetDetials){//歌单数据
            List<SongSheetDetials.ResultBean.TracksBean> songSheetInfo = ((SongSheetDetials) data).getResult().getTracks();
            return songSheetInfo ==null?0:songSheetInfo.size();
        }else if (data instanceof ArtistCenterInfo){
            List<ArtistCenterInfo.HotSongsBean> hotSongs = ((ArtistCenterInfo) data).getHotSongs();
            return hotSongs ==null?0:hotSongs.size();
        }else if (data instanceof SearchResult){//搜索
            List<SearchResult.ResultBean.SongsBean> songs = ((SearchResult) data).getResult().getSongs();
            return songs ==null?0:songs.size();
        }else {//本地音乐
            @SuppressWarnings("unchecked")
            List<MusicInfo> localMusicInfo = (List<MusicInfo>)data;
            return localMusicInfo ==null?0: localMusicInfo.size();
        }

    }

    /**
     * 设置音乐数据
     * @param data
     */
    public void setDatas(Object data) {
        this.data = data;
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
        @BindView(R.id.iv_is_downloaded)
        ImageView ivIsDownloaded;
        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
