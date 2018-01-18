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
import com.yzx.xiaomusic.utils.LoadingUtils;
import com.yzx.xiaomusic.utils.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by yzx on 2018/1/11.
 * Description
 */

public class DialogLocalMusicAdapter extends RecyclerView.Adapter<DialogLocalMusicAdapter.Holder> {

    private int[] titles;
    public static final String TAG ="yzxDialogLocalMusicAdapter";

    {
        titles = new int[]{R.string.collect, R.string.postMusic};
    }
    private MusicInfo musicInfo;
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
                default:
                    holder.tvTitle.setText(ResourceUtils.parseString(titles[i-1]));
                    break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (i) {
                    case 1://收藏

                        break;
                    case 2://上传音乐
                        checkFileIsExist(musicInfo);
                        Log.i("lll", "onClick: "+"上传音乐");
                        break;

                }
            }
        });
    }

    /**
     * 检查文件是否存在
     * @param musicInfo
     */
    private void checkFileIsExist(MusicInfo musicInfo) {


    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setDatas(MusicInfo musicInfo) {
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

    private void postMusic(MusicInfo musicInfo) {
        progressDialog.show();


    }
}
