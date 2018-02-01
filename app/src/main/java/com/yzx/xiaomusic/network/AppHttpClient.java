package com.yzx.xiaomusic.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yzx.xiaomusic.MusicApplication;
import com.yzx.xiaomusic.common.Constants;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.cache.CacheInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * app网络请求
 * Created by yzx on 2017/6/21.
 */

public class AppHttpClient {

    private static final String TAG = "logAppHttpClient";
    private static AppHttpClient mAppHttpClient;
    private Retrofit mRetrofit;
    public static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;
    public static final int MAX_AGE = 0;//5分钟5 * 6

    public static final int MAX_STALE = 5 * 24 * 60 * 60;//1天
    private Map<String, Object> serviceByType = new HashMap<>();

    private AppHttpClient() {
//        InitConfig.InitConfigInfo configInfo = FlyApplication.getApplication().getConfigInfo();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(getLogIntercepter())//添加日志拦截器
                .addInterceptor(getIntercepter())//添加拦截器
                .cache(getCache())//设置缓存
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)//baseurl
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        Log.i(TAG, "AppHttpClient: 请求"+Constants.BASE_URL);

//        LogUtils.d(AppHttpClient.class, "BaseUrl" + (configInfo == null ? Constants.BASE_URL : configInfo.getF_APIUrl()));
    }

    private Interceptor getLogIntercepter() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.i(TAG, "Request: "
                            +"\nheads:"+request.headers().toString()
                            +"\nmethod:"+request.method()
                            +"\nurl:"+request.url().toString()
                            +"\nrequestBody:"+request.body()
                );
                Response response = chain.proceed(request);
//                Log.d(TAG, "intercept: "+response.toString()
//                        +"\nheads:"+response.headers().toString()
//                        +"\nbody:"+response.peekBody(1024 * 1024).string());
                Log.i(TAG, String.format("返回信息: " +
                                "\n--------------------------------绚丽的分割线-------------------------------" +
                                "\nresponse: %s " +
                                "\n--------------------------------绚丽的分割线-------------------------------" +
                                " \nresponseHeads: %s " +
                                "\n--------------------------------绚丽的分割线-------------------------------" +
                                "\nresponseBody: %s",
                                response.toString(),
                                response.headers().toString(),
                                response.peekBody(1024 * 1024).string()));
                return response;
            }
        };
    }


    public static AppHttpClient getInstance() {
        if (mAppHttpClient == null) {
            synchronized (AppHttpClient.class) {
                if (mAppHttpClient == null) {
                    mAppHttpClient = new AppHttpClient();
                }
            }
        }
        return mAppHttpClient;
    }

    /**
     * 设置缓存
     *
     * @return
     */
    private Cache getCache() {

        final File cacheDir = new File(MusicApplication.getApplication().getExternalCacheDir(), "musicCache");
        return new Cache(cacheDir, MAX_CACHE_SIZE);
    }

    /**
     * 设置拦截器
     *
     * @return
     */
    private Interceptor getIntercepter() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetWorkUtils.isNetAvailable(MusicApplication.getApplication())) {
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }

                Response originalResponse = chain.proceed(request);

                if (NetWorkUtils.isNetAvailable(MusicApplication.getApplication())) {
                    return originalResponse.newBuilder().header("Cache-Control", "public,  max-age=" + MAX_AGE).build();
                } else {
                    return originalResponse.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + MAX_STALE).build();
                }
            }
        };
    }

    private CacheInterceptor getCacheIntercepter(){
        return null;
    }

    public synchronized <T> T getService(Class<T> apiInterface) {
        String serviceName = apiInterface.getName();
        if (!Check.isNull(serviceByType.get(serviceName))) {
            return (T) serviceByType.get(serviceName);
        }
        T service = mRetrofit.create(apiInterface);
        serviceByType.put(serviceName, service);
        return service;
    }
}
