package com.yzx.xiaomusic.ui.play;

import com.yzx.xiaomusic.common.BaseView;
import com.yzx.xiaomusic.common.observel.MvpObserver;
import com.yzx.xiaomusic.common.observel.OtherObserver;
import com.yzx.xiaomusic.entities.Lyric;
import com.yzx.xiaomusic.entities.SearchResult;

/**
 * Created by yzx on 2018/2/5.
 * Description
 */

public interface PlayContract {
    interface Presenter{
        void getLyrics(String id);

        void getLocalMusicLyrics(String musicName);
    }

    interface Model<V extends BaseView>{
        void getLyrics(V v,String id, OtherObserver<Lyric> observer);
        void getLgetLocalMusicLyricsyrics(V v,String musicName, MvpObserver<SearchResult> observer);
    }
}
