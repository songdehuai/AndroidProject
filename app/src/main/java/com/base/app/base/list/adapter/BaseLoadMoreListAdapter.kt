package com.base.app.base.list.adapter

import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.base.app.base.BaseBean
import com.base.app.base.BaseListBean

abstract class BaseLoadMoreListAdapter<T : BaseBean>(@LayoutRes private val layoutResId: Int, data: MutableList<T>? = null) : BaseListAdapter<T>(layoutResId, data), LoadMoreModule {


}