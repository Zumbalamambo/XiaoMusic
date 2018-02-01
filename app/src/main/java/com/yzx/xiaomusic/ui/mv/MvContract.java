package com.yzx.xiaomusic.ui.mv;

import com.yzx.xiaomusic.common.BaseView;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.BaseResposeBody;

/**
 * Created by yzx on 2018/1/30.
 * Description
 */

public interface MvContract {
    interface View extends BaseView{

    }

    interface Presenter{
        void getMvAddress(String mvId);
    }
    interface Model<V extends BaseView,B extends BaseResposeBody>{
        void getMvAddress(V v, String mvId, MvpObserver<B> observer);
    }
}
