package com.yzx.xiaomusic.ui.main.cloud.music.songsheetdetails;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.BaseResposeBody;
import com.yzx.xiaomusic.entities.SongSheetDetials;

/**
 * Created by yzx on 2018/1/19.
 * Description
 */

public interface SongSheetDetailsContract {

    interface View{

        /**
         * 获取歌单详情
         * @param id
         */
        void getSongSheetDetails(String id);

        /**
         * 设置数据
         * @param detials
         */
        void setDatas(SongSheetDetials detials);
    }

    interface Presenter{
        /**
         * 获取歌单详情
         * @param id
         */
        void getSongSheetDetails(String id);
    }

    interface Model<B extends BaseResposeBody>{
        /**
         * 获取歌单详情
         * @param id
         * @param observer
         */
        void getSongSheetDetails(String id, MvpObserver<B> observer);
    }
}
