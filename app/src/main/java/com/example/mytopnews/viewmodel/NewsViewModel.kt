package com.example.mytopnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mytopnews.model.data.NewsDataSourceFactory

class NewsViewModel(type:String) : ViewModel() {

    val stories = LivePagedListBuilder(NewsDataSourceFactory(type),
        PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false).build()).build()
}