package com.yzx.xiaomusic.ui.artistcenter;

import com.yzx.xiaomusic.common.BaseView;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.BaseResposeBody;

/**
 * Created by yzx on 2018/2/2.
 * Description
 */

public interface ArtistCenterContract {

    interface View extends BaseView{

    }

    interface Presenter{
        void getArtistCenterInfo(String artistId);
    }

    interface Model<V extends BaseView,B extends BaseResposeBody>{
        void getArtistInfo(V v, String artistId, MvpObserver<B> observer);
    }
}
