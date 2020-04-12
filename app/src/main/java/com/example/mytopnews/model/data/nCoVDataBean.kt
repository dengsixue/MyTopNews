package com.example.mytopnews.model.data

data class Data(
    val confirmedAdd: Int,//今日新增数
    val confirmedCount: Int,//总感染数
    val curedAdd: Int,//较昨日累计治愈
    val curedCount: Int,//累计治愈人数
    val currentConfirmedAdd: Int,//现存人数较昨日变化人数
    val currentConfirmedCount: Int,//现存确诊
    val deadCount: Int,//死亡总数
    val deathAdd: Int,//较昨日死亡人数
    val description: String,//病毒信息
    val severecasesAdd: Int,//现存重症较昨日
    val severecasesCount: Int,//现存重症
    val sourceDesc: String,//数据来源
    val suspectedAdd: Int,//境外输入较昨日变化人数
    val suspectedCount: Int,//境外输入
    val updateTime: String//数据更新时间
)