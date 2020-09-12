package com.example.mytopnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytopnews.model.data.repository.JokesRepository
import com.example.mytopnews.model.data.repository.NewsRepository

class JokeViewModel(private val jokesRepository: JokesRepository,time:String) : ViewModel(){

    private val mData = jokesRepository.getJokes(time)

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