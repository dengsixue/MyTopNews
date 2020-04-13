package com.example.mytopnews.model.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import java.util.concurrent.Executor

class NewsDataSourceFactory(private val str: String="top"): DataSource.Factory<String, News>() {
    val sourceLiveData = MutableLiveData<NewsDataSource>()
    override fun create(): DataSource<String, News>{
        val source = NewsDataSource(str)
        sourceLiveData.postValue(source)
        return source
    }
}