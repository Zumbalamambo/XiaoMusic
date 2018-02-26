package com.yzx.xiaomusic.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yzx.xiaomusic.db.entity.SongSheet;

import java.util.List;

/**
 * Created by yzx on 2018/2/26.
 * Description  歌单数据库操作接口
 */
@Dao
public interface SongSheetDAO {
    /**
     * 添加单个歌单
     * @param songSheet
     * @return
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSingleSongSheet(SongSheet ... songSheet);

    /**
     * 添加多个
     * @param songSheets
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addManySongSheet(List<SongSheet> songSheets);

    /**
     * 根据id查歌单
     * @param id
     * @return
     */
    @Query("select * from songSheet where id = :id")
    SongSheet getSongSheetById(String id);
}
