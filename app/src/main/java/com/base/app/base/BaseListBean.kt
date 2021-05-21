package com.base.app.base

import com.google.gson.annotations.SerializedName


data class BaseListBean<out T : BaseBean>(
    @SerializedName("listData")
    val itemList: List<T>?,
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("pageSize")
    val pageSize: Int = 0,
    @SerializedName("total")
    val totalCount: Int = 0,
)

