package com.example.mytopnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MineViewModel :ViewModel(){
    private val _text = MutableLiveData<String>().apply {
        value = "This is Mine"
    }
    val text: LiveData<String> = _text
}