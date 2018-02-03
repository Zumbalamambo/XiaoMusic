package com.yzx.xiaomusic.network.api;

import com.yzx.xiaomusic.entities.ArtistCenterInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yzx on 2018/2/2.
 * Description
 */

public interface ArtistApi {

    @GET("artists")
    Observable<ArtistCenterInfo> getArtistCenterInfo(@Query("id")String id);
}
