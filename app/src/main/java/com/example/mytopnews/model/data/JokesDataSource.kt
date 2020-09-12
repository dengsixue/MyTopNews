package com.example.mytopnews.model.data

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.example.mytopnews.model.remote.HttpClient
import com.example.mytopnews.model.remote.RemoteData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class JokesDataSource(val time:String) :ItemKeyedDataSource<String,Joke>() {


    private var retry: (() -> Any)? = null
    val netWorkState = MutableLiveData<NetworkState>()//网络状态
    val initialLoad = MutableLiveData<NetworkState>()//初始化状态

    private val mNewsServce by lazy {
        HttpClient.getInstance().paoService.getJokes(RemoteData.APIJOKE_KEY,"desc",1,20,time)
    }

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.also { it.invoke() }
    }
    @SuppressLint("CheckResult")
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<Joke>
    ) {
        //此次加载设置初始状态
        initialLoad.postValue(NetworkState.LOADING)
        netWorkState.postValue(NetworkState.HIDDEN)
        //加载数据
        mNewsServce.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe (
                {
                    //获得数据
                        t: HttpResponse<Joke>? -> callback.onResult(t?.result?.data!!)
                    //设置状态为已完成
                    initialLoad.postValue(NetworkState.LOADED)
                    netWorkState.postValue(NetworkState.LOADED)
                },
                {
                    //请求报错失败
                    initialLoad.postValue(NetworkState.FAILED)
                    //重试
                    retry={loadInitial(params, callback)}
                })
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<Joke>) {
        mNewsServce.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe (
                {
                        t: HttpResponse<Joke>? -> callback.onResult(t?.result?.data!!)
                    netWorkState.postValue(NetworkState.LOADED)},
                {
                    netWorkState.postValue(NetworkState.FAILED)
                    retry={loadAfter(params, callback)}
                })
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<Joke>) {}

    override fun getKey(item: Joke): String =item.hashId

}