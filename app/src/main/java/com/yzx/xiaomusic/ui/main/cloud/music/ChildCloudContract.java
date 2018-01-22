package com.yzx.xiaomusic.ui.main.cloud.music;

import com.yzx.xiaomusic.common.BaseActivity;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.Banner;
import com.yzx.xiaomusic.entities.SongSheet;

/**
 * Created by yzx on 2018/1/19.
 * Description
 */

public interface ChildCloudContract {

    interface View{

        /**
         * 获取歌单列表
         * @param cat
         * @param order
         * @param offset
         * @param limit
         * @param total
         */
        void getSongSheetList(String cat,String order,int offset,int limit,boolean total);

        /**
         * 获取Banner数据
         */
        void getBanner();

//        /**
//         * 设置数据
//         * @param songSheet
//         */
//        void setDatas(SongSheet songSheet);
    }

    interface Presenter{
        /**
         * 获取歌单列表
         * @param cat
         * @param order
         * @param offset
         * @param limit
         * @param total
         */
        void getSongSheetDetails(String cat,String order,int offset,int limit,boolean total);


        /**
         * 获取Banner数据
         */
        void getBanner();
    }

    interface Model<V extends BaseActivity>{
        /**
         *
         * @param cat
         * @param order
         * @param offset
         * @param limit
         * @param total
         * @param observer
         */
        void getSongSheetDetails(String cat,String order,int offset,int limit,boolean total, MvpObserver<SongSheet> observer);

        /**
         * 获取Banner数据
         */
        void getBanner(MvpObserver<Banner> observer);
    }
}
