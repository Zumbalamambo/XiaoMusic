package com.yzx.xiaomusic.ui.main.cloud.video;

import com.yzx.xiaomusic.common.BaseView;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.BaseResposeBody;
import com.yzx.xiaomusic.entities.MvData;

/**
 * Created by yzx on 2018/1/30.
 * Description
 */

public interface VideoCloudContract {
    interface View{
        void getVideoList(int offset,boolean isRefresh);

    }

    interface Presenter{
        void getVideoList(int offset,boolean isRefresh);
        void getMv(String id, int position);
    }

    interface Model<V extends BaseView,B extends BaseResposeBody>{
        void getVideoList(V v,int offset, MvpObserver<B> observer);
        void getMv(V v, String id, MvpObserver<MvData> observer);
    }
}
