package com.yzx.xiaomusic.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.entities.SearchResult;
import com.yzx.xiaomusic.utils.ResourceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yzx on 2018/2/22.
 * Description  选择码率下载文件
 */

public class BrDownloadDialog extends CustomAlertDialog{

    public static final String KEY_MUSIC_INFO = "musicInfo";
    @BindView(R.id.hMusic)
    TextView hMusic;
    @BindView(R.id.mMusic)
    TextView mMusic;
    @BindView(R.id.lMusic)
    TextView lMusic;
    Unbinder unbinder;
    private SearchResult.ResultBean.SongsBean songsBean;
    private OnChoiceListener onChoiceListener;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new NullPointerException("歌曲信息异常");
        } else {
            songsBean = (SearchResult.ResultBean.SongsBean) arguments.getSerializable(KEY_MUSIC_INFO);
        }
        @SuppressWarnings("ConstantConditions")
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_br_download, null);
        unbinder = ButterKnife.bind(this, view);
        builder.setView(view);
        View titleView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_custom_title, null);
        TextView title = (TextView) titleView.findViewById(R.id.tvDialogTitle);
        title.setText(R.string.download);
        builder.setCustomTitle(titleView);

        if (songsBean.getHMusic()!=null){
            hMusic.setVisibility(View.VISIBLE);
            SearchResult.ResultBean.SongsBean.HMusicBean hMusic = songsBean.getHMusic();
            this.hMusic.setText(String.format(ResourceUtils.parseString(R.string.br), Formatter.formatFileSize(getContext(), hMusic.getSize()),String.valueOf(hMusic.getBitrate()/1000)));
        }else {
            hMusic.setVisibility(View.GONE);
        }
        if (songsBean.getMMusic()!=null){
            mMusic.setVisibility(View.VISIBLE);
            SearchResult.ResultBean.SongsBean.MMusicBean mMusic = songsBean.getMMusic();
            this.mMusic.setText(String.format(ResourceUtils.parseString(R.string.br),Formatter.formatFileSize(getContext(), mMusic.getSize()),String.valueOf(mMusic.getBitrate()/1000)));
        }else {
            mMusic.setVisibility(View.GONE);
        }
        if (songsBean.getLMusic()!=null){
            lMusic.setVisibility(View.VISIBLE);
            SearchResult.ResultBean.SongsBean.LMusicBean lMusic = songsBean.getLMusic();
            this.lMusic.setText(String.format(ResourceUtils.parseString(R.string.br),Formatter.formatFileSize(getContext(), lMusic.getSize()),String.valueOf(lMusic.getBitrate()/1000)));
        }else {
            lMusic.setVisibility(View.GONE);
        }

        return builder.create();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.hMusic, R.id.mMusic, R.id.lMusic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hMusic:
                onChoiceListener.onChoice(songsBean.getHMusic().getBitrate());
                break;
            case R.id.mMusic:
                onChoiceListener.onChoice(songsBean.getMMusic().getBitrate());
                break;
            case R.id.lMusic:
                onChoiceListener.onChoice(songsBean.getLMusic().getBitrate());
                break;
        }
    }

    public void setOnChoiceListener(OnChoiceListener onChoiceListener){
        this.onChoiceListener = onChoiceListener;
    }

    public interface OnChoiceListener{
        void onChoice(int br);
    }
}
