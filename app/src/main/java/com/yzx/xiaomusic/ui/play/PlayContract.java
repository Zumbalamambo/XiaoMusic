package com.yzx.xiaomusic.ui.play;

import com.yzx.xiaomusic.common.BaseView;
import com.yzx.xiaomusic.common.observel.OtherObserver;
import com.yzx.xiaomusic.entities.Lyric;

/**
 * Created by yzx on 2018/2/5.
 * Description
 */

public interface PlayContract {
    interface Presenter{
        void getLyrics(String id);
    }

    interface Model<V extends BaseView>{
        void getLyrics(V v,String id, OtherObserver<Lyric> observer);
    }
}
