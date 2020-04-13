package com.example.mytopnews.model.data.repository

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mytopnews.model.data.Listing
import com.example.mytopnews.model.data.News
import com.example.mytopnews.model.data.NewsDataSourceFactory

class NewsRepository {
    companion object {

        private const val PAGE_SIZE = 10

        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: NewsRepository().also { instance = it }
            }
    }

    fun getNews(type:String): Listing<News> {
        val sourceFactory = NewsDataSourceFactory(type)
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(PAGE_SIZE)//设置初始加载大小
            .setEnablePlaceholders(false)
            .build()
        val livePageList = LivePagedListBuilder<String,News>(sourceFactory,config).build()
        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) { it.initialLoad }
        return Listing(
            pagedList = livePageList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) { it.netWorkState },
            retry = { sourceFactory.sourceLiveData.value?.retryAllFailed() },
            refresh = { sourceFactory.sourceLiveData.value?.invalidate() },
            refreshState = refreshState
        )
    }
}