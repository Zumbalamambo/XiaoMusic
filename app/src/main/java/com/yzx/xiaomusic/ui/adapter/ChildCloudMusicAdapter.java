package com.yzx.xiaomusic.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.yzx.xiaomusic.R;
import com.yzx.xiaomusic.entities.Banner;
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.utils.GlideUtils;
import com.yzx.xiaomusic.utils.ResourceUtils;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzx on 2018/1/19.
 * Description
 */

public class ChildCloudMusicAdapter extends DelegateAdapter.Adapter<ChildCloudMusicAdapter.Holder>{


    public static final int TYPE_BANNER = 1;
    public static final int TYPE_THE_FOUR = 2;
    public static final int TYPE_SONG_SHEET = 3;
    public static final String KEY_SONG_SHEET = "songSheet";
    private static final String TAG = "yglChildCloudAdapter";
    private LayoutHelper layoutHelper;
    private int count;
    private ViewGroup.LayoutParams layoutParams;
    private SongSheet songSheet;
    private ArrayList<Integer> theFourIcons;
    private ArrayList<Integer> theFourTitles;
    private OnItemClickLsitener onItemClickListener;
    private List<Banner.BannersBean> banners;

    {
        theFourIcons = new ArrayList<>();
        theFourTitles = new ArrayList<>();
        theFourIcons.add(R.drawable.ic_privatefm);
        theFourIcons.add(R.drawable.ic_day_recommend);
        theFourIcons.add(R.drawable.ic_song_sheet);
        theFourIcons.add(R.drawable.ic_rank);
        theFourTitles.add(R.string.privateFM);
        theFourTitles.add(R.string.dayRecommend);
        theFourTitles.add(R.string.songSheet);
        theFourTitles.add(R.string.rank);
    }

    public ChildCloudMusicAdapter(LayoutHelper layoutHelper, int count) {
        this(layoutHelper, count, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public ChildCloudMusicAdapter(LayoutHelper layoutHelper, int count, @NonNull ViewGroup.LayoutParams layoutParams) {
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        switch (viewType){
            case TYPE_BANNER:
                return new Holder(LayoutInflater.from(context).inflate(R.layout.item_music_child_banner, parent, false));
            case TYPE_THE_FOUR:
                return new Holder(LayoutInflater.from(context).inflate(R.layout.item_music_child_four, parent, false));
            case TYPE_SONG_SHEET:
                return new Holder(LayoutInflater.from(context).inflate(R.layout.item_music_child_song_sheet, parent, false));
                default:
                    return new Holder(LayoutInflater.from(context).inflate(R.layout.item_music_child_tite,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        switch (getItemViewType(position)){
            case TYPE_BANNER:
                setBannerData(holder,position);
                break;
            case TYPE_THE_FOUR:
                setTheFourData(holder,position);
                break;
            case TYPE_SONG_SHEET:
                setSongSheetData(holder,position);
                break;
            default:
                break;
        }
    }


    /**
     * 设置banner数据
     * @param holder
     * @param position
     */
    private void setBannerData(Holder holder, int position) {

        if (banners!=null){

            holder.bannerView.setPages(banners, new MZHolderCreator<BannerViewHolder>() {
                @Override
                public BannerViewHolder createViewHolder() {
                    return new BannerViewHolder();
                }
            });
            holder.bannerView.start();
        }
    }

    /**
     * 设置theFour信息
     * @param holder
     * @param position
     */
    private void setTheFourData(final Holder holder, final int position) {

        GlideUtils.loadImg(holder.itemView.getContext(),theFourIcons.get(position),holder.ivIcon);
        holder.tvTitle.setText(ResourceUtils.parseString(theFourTitles.get(position)));
        holder.ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onItemClickListener.onItemClickListener(holder.itemView,position,null,TYPE_THE_FOUR);
            }
        });
    }
    /**
     * 设置歌单信息
     * @param holder
     * @param position
     */
    private void setSongSheetData(final Holder holder, final int position) {
        if (songSheet != null) {
            final SongSheet.PlaylistsBean playlistsBean = songSheet.getPlaylists().get(position);
            holder.tvSongSheetDes.setText(playlistsBean.getName());
            if (playlistsBean.getPlayCount()>100000){
                holder.tvSongSheetNum.setText(String.format("%s%s",
                        String.valueOf(playlistsBean.getPlayCount()/10000),
                        ResourceUtils.parseString(R.string.TenThousand)));
            }else {
                holder.tvSongSheetNum.setText(String.valueOf(playlistsBean.getPlayCount()));
            }
            final Context context = holder.itemView.getContext();
            GlideUtils.loadImg(context,playlistsBean.getCoverImgUrl(),holder.ivSongSheetBg);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onItemClickListener.onItemClickListener(holder.itemView,position,playlistsBean, TYPE_SONG_SHEET);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    /**
     * 设置歌单数据
     * @param songSheet
     */
    public void setSongSheetDatas(SongSheet songSheet) {
        this.songSheet = songSheet;
        notifyDataSetChanged();
    }

    /**
     * 设置Banner数据
     * @param banners
     */
    public void setBannerDatas(List<Banner.BannersBean> banners){
        this.banners = banners;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickLsitener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    //创建ViewHolder
   public class Holder extends RecyclerView.ViewHolder {

        private MZBannerView bannerView;
        public volatile int existing = 0;
        public int createdTime = 0;
        private TextView tvSongSheetDes;
        private TextView tvTitle;
        private ImageView ivIcon;
        private TextView tvSongSheetNum;
        private ImageView ivSongSheetBg;

        public Holder(View itemView) {
            super(itemView);
            createdTime++;
            existing++;
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);

            bannerView = (MZBannerView) itemView.findViewById(R.id.bannerView);

            tvSongSheetDes = (TextView) itemView.findViewById(R.id.tv_songSheetDes);
            ivSongSheetBg = (ImageView) itemView.findViewById(R.id.iv_songSheetBg);
            tvSongSheetNum = (TextView) itemView.findViewById(R.id.tv_songSheetNum);

        }

        @Override
        protected void finalize() throws Throwable {
            existing--;
            super.finalize();
        }
    }


    public class BannerViewHolder implements MZViewHolder<Banner.BannersBean> {
        private ImageView mImageView;
        private TextView bannerType;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            bannerType = (TextView) view.findViewById(R.id.tv_banner_type);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Banner.BannersBean data) {
            // 数据绑定
            GlideUtils.loadImg(context,data.getPic(),mImageView);
            bannerType.setText(data.getTypeTitle());
        }
    }

    public interface OnItemClickLsitener{
        void onItemClickListener(View itemView, int position, SongSheet.PlaylistsBean playlistsBean, int type);
    }
}
