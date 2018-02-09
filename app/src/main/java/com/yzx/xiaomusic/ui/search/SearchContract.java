package com.yzx.xiaomusic.ui.search;

import com.yzx.xiaomusic.common.BaseView;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.entities.BaseResposeBody;

/**
 * Created by yzx on 2018/2/9.
 * Description
 */

public interface SearchContract {
    interface Presenter{
        void search(String keyWord);
    }

    interface Model<V extends BaseView,B extends BaseResposeBody>{
        void search(V v, String keyWord, MvpObserver<B> observer);
    }
}
