package com.example.mytopnews.model.remote

import com.example.mytopnews.model.data.Data
import com.example.mytopnews.model.data.HttpResponse
import com.example.mytopnews.model.data.News
import com.example.mytopnews.util.Constants
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface PaoService {
    @GET ("toutiao/index")
    fun getNews(@Query("type") type:String, @Query("key") key:String):Observable<HttpResponse>
//    fun getNews():Observable<HttpResponse>

    @Headers(
        "authoration:apicode",
        "apicode:${RemoteData.APILINK_KEY}"
    )
    @GET("apis/dst/ncov/country")
    fun getNcovData():Observable<Data>
}
