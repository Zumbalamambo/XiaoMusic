package com.yzx.xiaomusic.network.api;


import com.yzx.xiaomusic.entities.Banner;
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.entities.SongSheetDetials;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yzx on 2018/1/17.
 * Description
 */

public interface MuiscApi {

    /**
     * 歌单推荐
     * @param cat
     * @param order
     * @param offset
     * @param total
     * @param limit
     * @return
     */
    @GET("top/playlist")
    Observable<SongSheet> getSongSheet(@Query("cat") String cat, @Query("order") String order, @Query("offset") int offset,
                                       @Query("total") boolean total, @Query("limit") int limit);

    /**
     * 获取歌单详情
     * @param id
     * @return
     */
    @GET("http://music.163.com/api/playlist/detail")
    Observable<SongSheetDetials> getSongSheetDetails(@Query("id") String id);

    /**
     * 获取Banner
     * @return
     */
    @GET("banner")
    Observable<Banner> getBanner();
}
