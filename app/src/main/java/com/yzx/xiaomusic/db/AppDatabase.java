package com.yzx.xiaomusic.db;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.yzx.xiaomusic.MusicApplication;
import com.yzx.xiaomusic.db.dao.MusicDAO;
import com.yzx.xiaomusic.db.dao.SongSheetDAO;
import com.yzx.xiaomusic.db.entity.Music;
import com.yzx.xiaomusic.db.entity.SongSheet;

/**
 *
 * @author yzx
 * @date 2018/2/26
 * Description
 */
@Database(entities = {Music.class, SongSheet.class},version = 2,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDataBase;

    public static AppDatabase getInstance(){
        if (appDataBase==null){
            appDataBase = Room.databaseBuilder(MusicApplication.getApplication(), AppDatabase.class, "xiaoMusic")
                    .allowMainThreadQueries()
                    .build();
        }
        return appDataBase;
    }

    /**
     * called when the RoomDatabase is initialized.
     * @param config
     * @return
     */
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {

        return null;
    }

    /**
     * Called when the RoomDatabase is created.
     * @return
     */
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    public abstract MusicDAO getMusicDAO();

    public abstract SongSheetDAO getSongSheetDAO();
}
