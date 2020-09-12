package com.example.mytopnews.model.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class JokesDataSourceFactory(private val time:String="00000000000"): DataSource.Factory<String,Joke>() {
    val sourceLiveData = MutableLiveData<JokesDataSource>()
    override fun create(): DataSource<String, Joke>{
        val source = JokesDataSource(time)
        sourceLiveData.postValue(source)
        return source
    }

}