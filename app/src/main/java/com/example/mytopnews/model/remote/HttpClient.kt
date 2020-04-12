package com.example.mytopnews.model.remote

import com.example.mytopnews.util.Constants
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.cache.CacheInterceptor
import okhttp3.internal.cache.InternalCache
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import android.app.Application as Application

class HttpClient private constructor(){
    companion object {
        @Volatile
        private var instance: HttpClient? = null

        fun getInstance(): HttpClient {
            return instance ?: synchronized(this) {
                instance ?: HttpClient().also { instance = it }
            }
        }
    }
    internal val paoService: PaoService by lazy {
        create(RemoteData.BASE_URL)
    }
    internal val ApiLinkService: PaoService by lazy {
        create(RemoteData.APILINK_BASE_URL)
    }


//    val instance: PaoService = create(BASE_URL)

    private fun create(baseUrl:String): PaoService {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        //缓存时间
//        val CACHE_TIMEOUT:Long = 10 * 1024 * 1024
        //缓存存放的文件
//        val httpCacheDirectory:File = File(Application().getCacheDir(), "goldze_cache")
//        //缓存对象
//        val cache:Cache = Cache(httpCacheDirectory, CACHE_TIMEOUT)

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
//            .cache(cache)
//            .addInterceptor(CacheInterceptor(Application()))
            .build()


        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PaoService::class.java)
    }
}