package com.yzx.xiaomusic.ui.main.cloud.music;

import com.yzx.xiaomusic.common.BaseActivity;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.BaseResposeBody;
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
         * 设置数据
         * @param songSheet
         */
        void setDatas(SongSheet songSheet);
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
    }

    interface Model<B extends BaseResposeBody,V extends BaseActivity>{
        /**
         *
         * @param cat
         * @param order
         * @param offset
         * @param limit
         * @param total
         * @param observer
         */
        void getSongSheetDetails(String cat,String order,int offset,int limit,boolean total, MvpObserver<B> observer);
    }
}
