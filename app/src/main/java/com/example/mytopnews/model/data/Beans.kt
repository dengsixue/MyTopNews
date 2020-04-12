package com.example.mytopnews.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class HttpResponse(
    var reason:String,
    var result:com.example.mytopnews.model.data.Result,
    var error_code:Int
)

data class News(

    val author_name: String,

    val category: String,

    val date: String,

    val thumbnail_pic_s: String,

    val thumbnail_pic_s02: String,

    val thumbnail_pic_s03: String,

    val title: String,

    val uniquekey: String,

    val url: String
)
data class Result(
    var stat:String,
    var data:ArrayList<News>
)