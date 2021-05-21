package com.base.app.base.list.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.base.app.base.BaseBean

abstract class BaseBindingLoadMoreListAdapter<T : BaseBean, Binding : ViewDataBinding>(@LayoutRes private val layoutResId: Int, data: MutableList<T>? = null) : BaseQuickAdapter<T, BaseDataBindingHolder<Binding>>(layoutResId, data), LoadMoreModule {


}
