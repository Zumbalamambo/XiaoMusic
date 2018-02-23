package com.yzx.xiaomusic.network.api;


import com.yzx.xiaomusic.entities.Banner;
import com.yzx.xiaomusic.entities.Lyric;
import com.yzx.xiaomusic.entities.MusicAddress;
import com.yzx.xiaomusic.entities.MvData;
import com.yzx.xiaomusic.entities.SearchResult;
import com.yzx.xiaomusic.entities.SongSheet;
import com.yzx.xiaomusic.entities.SongSheetDetials;
import com.yzx.xiaomusic.entities.VideoList;

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


    /**
     * 获取歌曲地址
     * @param type
     * @param id
     * @param br
     * @return
     */
    @GET("https://api.imjad.cn/cloudmusic")
    Observable<MusicAddress> getMusicAddress(@Query("type") String type,@Query("id") String id,@Query("br") int br);

    /**
     *
     * @param type
     * @param id
     * @param br
     * @return
     */
    @GET("https://api.imjad.cn/cloudmusic")
    Observable<MvData> getMusicMv(@Query("type") String type, @Query("id") String id, @Query("br") String br);


    /**
     * 获取
     * @param offset
     * @param limit
     * @return
     */
    @GET("http://musicapi.leanapp.cn/top/mv")
    Observable<VideoList> getVideoList(@Query("offset") int offset, @Query("limit") int limit);

    /**
     * 获取歌词
     * @param id
     * @return
     */
    @GET("https://api.imjad.cn/cloudmusic")
    Observable<Lyric> getMusicLyrics(@Query("type") String type,@Query("id") String id);


    /**
     * 搜索歌曲
     * @param type
     * @param keywords
     * @param offset
     * @param limit
     * @return
     */
    @GET("http://musicapi.leanapp.cn/search")
    Observable<SearchResult> searchMusic(@Query("type") String type, @Query("keywords") String keywords, @Query("offset") int offset,@Query("limit") int limit);

}
