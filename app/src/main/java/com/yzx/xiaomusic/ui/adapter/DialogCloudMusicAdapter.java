package com.yzx.xiaomusic.ui.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.entities.MusicInfo;
import com.yzx.xiaomusic.entities.SongSheetDetials;
import com.yzx.xiaomusic.utils.LoadingUtils;
import com.yzx.xiaomusic.utils.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yzx on 2018/1/11.
 * Description
 */

public class DialogCloudMusicAdapter extends RecyclerView.Adapter<DialogCloudMusicAdapter.Holder> {

    private int[] titles;
    {
        titles = new int[]{R.string.downloadMusic, R.string.postMusic};
    }
    private SongSheetDetials.ResultBean.TracksBean musicInfo;
    private ProgressDialog progressDialog;
    private Context context;
    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        progressDialog = LoadingUtils.showLoadingDialog(viewGroup.getContext());
        return new Holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dialog_music_local, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, final int i) {
        switch (i) {
            case 0:
                holder.ivIcon.setVisibility(View.GONE);
                holder.tvTitle.setTextSize(10);
                holder.tvTitle.setText(String.format(ResourceUtils.parseString(R.string.music),musicInfo.getName()));
                break;
            case 2:
//                holder.tvTitle.setText(Formatter.formatFileSize(context,musicInfo.getSize()));
                break;
                default:
                    holder.tvTitle.setText(ResourceUtils.parseString(titles[i-1]));
                    break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (i) {
                    case 1://下载音乐
//                        downLoadMusic(musicInfo);
                        Log.i("lll", "onClick: "+musicInfo.getName());
                        break;
                }
            }
        });
    }

    /**
     * 下载音乐
     * @param musicInfo
     */
    private void downLoadMusic(MusicInfo musicInfo) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setDatas(SongSheetDetials.ResultBean.TracksBean musicInfo) {
        this.musicInfo = musicInfo;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
