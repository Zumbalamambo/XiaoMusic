package com.yzx.xiaomusic.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yzx.xiaomusic.db.entity.Music;

import java.util.List;

import io.reactivex.Flowable;

/**
 *
 * @author yzx
 * @date 2018/2/26
 * Description
 */

@Dao
public interface MusicDAO {
    /**
     * 查询所有的歌曲
     * @return
     */
    @Query("select * from music")
    Flowable<List<Music>> getAllMusic();

    /**
     * 添加音乐
     * @param music
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMuisc(Music...music);
}
