package com.example.mytopnews.model.remote

import com.example.mytopnews.model.data.Data
import com.example.mytopnews.model.data.HttpResponse
import com.example.mytopnews.model.data.Joke
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
    /**
    * 新闻api
    * */
    @GET ("toutiao/index")
    fun getNews(@Query("type") type:String, @Query("key") key:String):Observable<HttpResponse<News>?>
//    fun getNews():Observable<HttpResponse>
    /**
     * 新冠api
     * */
    @Headers(
        "authoration:apicode",
        "apicode:${RemoteData.APILINK_KEY}"
    )
    @GET("apis/dst/ncov/country")
    fun getNcovData():Observable<Data>

    @GET("joke/content/list.php")
    fun getJokes(@Query("sort")sort:String,//类型，desc:指定时间之前发布的，asc:指定时间之后发布的
                 @Query("page")page:Int,//当前页数,默认1,最大20
                 @Query("pagesize")pageSize:Int,//每次返回条数,默认1,最大20
                 @Query("time")time:String):Observable<HttpResponse<Joke>>//时间戳（10位），如：1418816972
}
