package com.yzx.xiaomusic.ui.main.cloud.music.songsheetlist;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.common.BaseFragment;
import com.yzx.xiaomusic.entities.MusicMessage;
import com.yzx.xiaomusic.entities.PlayEvent;
import com.yzx.xiaomusic.entities.ProgressInfo;
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.ui.adapter.ChildCloudMusicAdapter;
import com.yzx.xiaomusic.ui.adapter.SongSheetAdapter;
import com.yzx.xiaomusic.ui.dialog.MusicMenuDialog;
import com.yzx.xiaomusic.ui.main.MainFragment;
import com.yzx.xiaomusic.ui.main.cloud.music.songsheetdetails.SongSheetDetailsFragment;
import com.yzx.xiaomusic.ui.play.PlayFragment;
import com.yzx.xiaomusic.utils.DensityUtils;
import com.yzx.xiaomusic.utils.GlideUtils;
import com.yzx.xiaomusic.widget.CircleProgress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yzx on 2018/1/12.
 * Description  歌单列表
 */

public class SongSheetListFragment extends BaseFragment implements SongSheetListContract.View, ChildCloudMusicAdapter.OnItemClickLsitener, XRecyclerView.LoadingListener{
    private static final String TAG = "yglSongSheetListFragment";
    private static final String KEY_SONG_SHEET = "songSheet";
    private static SongSheetListFragment childCloudFragment;
    @BindView(R.id.recyclerView)
    XRecyclerView recyclerView;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.layout_music_control)
    LinearLayout layoutMusicControl;
    @BindView(R.id.iv_music_poster)
    ImageView ivMusicPoster;
    @BindView(R.id.tv_music_name)
    TextView tvMusicName;
    @BindView(R.id.tv_music_artist)
    TextView tvMusicArtist;
    @BindView(R.id.circleProgress)
    CircleProgress circleProgress;
    private SongSheetListPresenter mPresenter;
    public SongSheetAdapter adapter;
    public int offset;

    @SuppressLint("ValidFragment")
    private SongSheetListFragment() {
    }

    public static SongSheetListFragment getInstance() {
        if (childCloudFragment == null) {
            childCloudFragment = new SongSheetListFragment();
        }
        return childCloudFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cloud_song_sheet;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mPresenter = new SongSheetListPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setToolBar(toolBar, R.string.songSheet);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        View headView = getLayoutInflater().inflate(R.layout.head_song_sheet, null);

        recyclerView.addHeaderView(headView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int margin = (int) DensityUtils.dip2px(3);
                outRect.set(margin, 0, margin, 0);
            }
        });
        adapter = new SongSheetAdapter();
        adapter.setOnItemClickListener(new SongSheetAdapter.OnItemClickLsitener() {
            @Override
            public void onItemClickListener(View itemView, int position, SongSheet.PlaylistsBean playlistsBean) {
                SongSheetDetailsFragment songSheetFragment = SongSheetDetailsFragment.getInstance();
                Bundle args = new Bundle();
                args.putSerializable(KEY_SONG_SHEET, playlistsBean);
                songSheetFragment.setArguments(args);
                start(songSheetFragment);
            }
        });
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingListener(this);
        recyclerView.setAdapter(adapter);
        offset = 0;
        getSongSheetList("全部", "hot", offset, 10, true);
    }

    @Override
    public void getSongSheetList(String cat, String order, int offset, int limit, boolean total) {
        mPresenter.getSongSheetDetails(cat, order, offset, limit, total);
    }

    @Override
    public void onItemClickListener(View itemView, int position, SongSheet.PlaylistsBean playlistsBean, int type) {

        MainFragment parentFragment = (MainFragment) getParentFragment().getParentFragment();
        SongSheetDetailsFragment songSheetFragment = SongSheetDetailsFragment.getInstance();
        Bundle args = new Bundle();
        args.putSerializable(KEY_SONG_SHEET, playlistsBean);
        songSheetFragment.setArguments(args);
        parentFragment.start(songSheetFragment);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        getSongSheetList("全部", "hot", offset, 10, true);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        setUpBottomPlayControl(tvMusicName,tvMusicArtist, circleProgress, ivMusicPoster);
    }

    @OnClick({R.id.circleProgress,R.id.iv_music_menu, R.id.layout_music_control})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.circleProgress:
                getPlayService().playMusic();
                break;
            case R.id.iv_music_menu:
                MusicMenuDialog dialog=new MusicMenuDialog();
                dialog.show(getActivity().getSupportFragmentManager(),"musicMuenu");
                break;
            case R.id.layout_music_control:
                start(PlayFragment.getInstance());
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PlayEvent event) {
        switch (event.type){
            case PlayEvent.TYPE_CHANGE:
                MusicMessage musicMessage = (MusicMessage) event.getData();
                tvMusicName.setText(musicMessage.getName());
                tvMusicArtist.setText(musicMessage.getArtist());
                GlideUtils.loadImg(context,musicMessage.getPoster(),GlideUtils.TYPE_DEFAULT,ivMusicPoster);
                break;
            case PlayEvent.TYPE_PLAY:
                circleProgress.setState(CircleProgress.STATE_PLAY);
                break;
            case PlayEvent.TYPE_PAUSE:
                circleProgress.setState(CircleProgress.STATE_PAUSE);
                break;
            case PlayEvent.TYPE_PROCESS:

                ProgressInfo progressInfo = (ProgressInfo) event.getData();
//                Log.i(TAG, progressInfo.getProcess()+"onMessageEvent: "+progressInfo.getDuration());
                circleProgress.setMax((int) progressInfo.getDuration());
                circleProgress.setProgress(progressInfo.getProcess());
                break;
            default:
                break;
        }

    };

}
