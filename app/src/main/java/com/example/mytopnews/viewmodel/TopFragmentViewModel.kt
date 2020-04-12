package com.example.mytopnews.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mytopnews.model.data.repository.NewsRepository

class TopFragmentViewModel (
    private val newsRespository: NewsRepository,
     type:String
    ) : ViewModel() {

        private val mData = newsRespository.getNews(type)

        val newsList = mData.pagedList

        val netWorkState = mData.networkState

        val refreshState = mData.refreshState

        fun refresh() {
            mData.refresh.invoke()
        }

        fun retry() {
            mData.retry.invoke()
        }
}