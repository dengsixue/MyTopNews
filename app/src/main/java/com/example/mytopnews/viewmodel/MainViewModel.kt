package com.example.mytopnews.viewmodel

import android.annotation.SuppressLint
import androidx.databinding.ObservableField
import com.example.mytopnews.model.data.HttpResponse
import com.example.mytopnews.model.data.News
import com.example.mytopnews.model.remote.PaoService
import com.example.mytopnews.model.remote.RemoteData.NEWS_TYPE_CAIJING
import com.example.mytopnews.model.remote.RemoteData.URL_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(val remote: PaoService) {
    val info = ObservableField<String>()
    @SuppressLint("CheckResult")
    fun LoadNews(){
        remote.getNews(
            NEWS_TYPE_CAIJING, URL_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({t: HttpResponse<News>? -> info.set(t?.result?.data?.get(0)?.url?.toString())}
            ,{t:Throwable?->info.set(t?.message?:"error")})

    }
}