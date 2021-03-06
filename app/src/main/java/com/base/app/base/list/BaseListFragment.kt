package com.base.app.base.list

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.base.app.R
import com.base.app.base.BaseActivity
import com.base.app.base.BaseBean
import com.base.app.base.BaseFragment
import com.base.app.base.BaseListBean
import com.base.app.base.list.adapter.BaseListAdapter
import com.base.app.base.list.adapter.BaseLoadMoreListAdapter
import com.base.app.ext.ui
import com.base.app.global.Constant
import com.base.app.http.Result
import com.base.app.http.result
import io.reactivex.Observable


abstract class BaseListFragment<T : BaseBean> : BaseFragment() {

    private val activity by lazy { getActivity() as BaseActivity }
    private val rvList by lazy { mView.findViewById<RecyclerView>(R.id.rv_list) }
    private val rflList by lazy { mView.findViewById<SwipeRefreshLayout>(R.id.rfl_list) }
    private lateinit var mAdapter: BaseQuickAdapter<T, *>
    private var mPage = Constant.PageSize.DEFAULT_PAGE
    private var mPageSize = Constant.PageSize.DEFAULT_PAGE_SIZE
    private var isLoadMore = false
    private lateinit var resultList: Result<BaseListBean<T>>

    override fun getLayout(container: ViewGroup?): Any {
        return R.layout.fragment_base_list
    }

    override fun init(savedInstanceState: Bundle?) {
        initData()
        initViews()
        onInit(savedInstanceState)
    }


    private fun initViews() {
        rflList.setColorSchemeColors(ContextCompat.getColor(activity, R.color.main))
        rflList.setOnRefreshListener {
            getListData()
        }
        if (isLoadMore) {
            mAdapter.loadMoreModule.setOnLoadMoreListener {
                loadMore()
            }
        }
        rvList.adapter = mAdapter
        if (isAutoLoad()) {
            rflList.isRefreshing = true
            getListData()
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            onItemClick(adapter, view, position)
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            onItemChildClickListener(adapter, view, position)
        }
        mAdapter.setOnItemLongClickListener { adapter, view, position ->
            onItemLongClickListener(adapter, view, position)
        }
        mAdapter.setOnItemChildLongClickListener { adapter, view, position ->
            onItemChildLongClickListener(adapter, view, position)
        }

    }

    private fun initData() {
        mAdapter = getAdapter()
        isLoadMore = mAdapter is BaseLoadMoreListAdapter
        loadMoreAttach()
    }

    private fun getListData() {
        mPage = Constant.PageSize.DEFAULT_PAGE
        mPageSize = Constant.PageSize.DEFAULT_PAGE_SIZE
        getApi(mPage, mPageSize)
            .compose(activity.lift())
            .doFinally { ui { rflList.isRefreshing = false } }
            .result { result ->
                resultList = result
                result.success {
                    mAdapter.data.clear()
                    result.data?.itemList?.let { list -> mAdapter.data.addAll(list);mAdapter.notifyDataSetChanged() }
                    loadMoreAttach()
                }.error {

                }
            }
    }

    private fun loadMore() {
        // TODO: 2021/5/18 ??????{BaseRecyclerViewAdapterHelper:3.0.4}???????????????AutoLoadMore,??????????????????????????????
        if (this::resultList.isInitialized) {
            resultList.data?.totalCount?.let {
                if (mAdapter.data.size >= it) {
                    mAdapter.loadMoreModule.loadMoreEnd()
                    loadMoreClose()
                }
                return
            }
        }
        mPage += 1
        getApi(mPage, mPageSize)
            .compose(activity.lift())
            .doFinally { ui { rflList.isRefreshing = false;mAdapter.loadMoreModule.loadMoreComplete() } }
            .result { result ->
                result.success {
                    result.data?.let { data ->
                        data.itemList?.let { list -> mAdapter.data.addAll(list) }
                        mAdapter.notifyDataSetChanged()
                        loadMoreComplete()
                        if (mAdapter.data.size >= data.totalCount) {
                            loadMoreClose()
                        }
                    }
                }.error {
                    mPage -= 1
                    loadMoreFail()
                }
            }
    }

    private fun loadMoreAttach() {
        if (isLoadMore) {
            if (this::resultList.isInitialized) {
                resultList.data?.totalCount?.let {
                    if (mAdapter.data.size >= it) {
                        mAdapter.loadMoreModule.isEnableLoadMore = false
                    }
                    return
                }
            }
            mAdapter.loadMoreModule.isAutoLoadMore = true
            mAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
            mAdapter.loadMoreModule.isEnableLoadMore = true
        }
    }

    private fun loadMoreClose() {
        if (isLoadMore) {
            mAdapter.loadMoreModule.isEnableLoadMore = false
        }
    }

    private fun loadMoreComplete() {
        if (isLoadMore) {
            mAdapter.loadMoreModule.loadMoreComplete()
        }
    }

    private fun loadMoreFail() {
        if (isLoadMore) {
            mAdapter.loadMoreModule.loadMoreFail()
        }
    }


    abstract fun getApi(page: Int, size: Int): Observable<Result<BaseListBean<T>>>

    abstract fun getAdapter(): BaseListAdapter<T>

    fun getItemData(position: Int): T {
        return mAdapter.getItem(position)
    }


    open fun isAutoLoad(): Boolean {
        return true
    }

    open fun onInit(savedInstanceState: Bundle?) {}

    open fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {}

    open fun onItemChildClickListener(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {}

    open fun onItemLongClickListener(adapter: BaseQuickAdapter<*, *>, view: View, position: Int): Boolean {
        return false
    }

    open fun onItemChildLongClickListener(adapter: BaseQuickAdapter<*, *>, view: View, position: Int): Boolean {
        return false
    }


}





