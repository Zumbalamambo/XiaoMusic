package com.yzx.xiaomusic.common.musicaddress;

import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.MusicAddress;

/**
 * Created by yzx on 2018/1/23.
 * Description
 */

public interface MusicAddressContract {
    interface View{
        /**
         * 获取音乐Id
         * @param id
         */
        void getMusicAddress(String id);
    }

    interface Presenter{
        /**
         * 获取音乐Id
         * @param id
         */
        void getMusicAddress(String id);
    }

    interface Model{
        /**
         * 获取音乐Id
         * @param id
         */
        void getMusicAddress(String id, MvpObserver<MusicAddress> observer);
    }
}
