package com.example.mytopnews.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.mytopnews.model.data.repository.NewsRepository

//class NewsListViewModel(application: Application, postRepository: NewsRepository) :
//    AndroidViewModel(application) {
//    private var mPostRepository: NewsRepository = postRepository
//
//    // region 基于Android官方Paging Library的分页加载框架
//    private val data = MutableLiveData<String>()
//    private val repoResult = Transformations.map(data) {
//        mPostRepository.getNewsList(10)
//    }
//
//    val posts = Transformations.switchMap(repoResult, { it.pagedList })
//    val networkState = Transformations.switchMap(repoResult, { it.networkState })
//    val refreshState = Transformations.switchMap(repoResult, { it.refreshState })
//
//    fun refresh() {
//        repoResult.value?.refresh?.invoke()
//    }
//
//    fun showDatas(subreddit: String): Boolean {
//        if (data.value == subreddit) {
//            return false
//        }
//        data.value = subreddit
//        return true
//    }
//
//    fun retry() {
//        val listing = repoResult.value
//        listing?.retry?.invoke()
//    }
//
//    fun currentData(): String? = data.value
//
//    // endregion
//}