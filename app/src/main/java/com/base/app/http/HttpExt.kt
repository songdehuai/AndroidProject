package com.base.app.http

import androidx.appcompat.app.AppCompatActivity
import com.base.app.base.BaseBean
import com.base.app.base.BaseListBean
import com.base.app.http.subscriber.DialogSubscriber
import com.base.app.http.subscriber.ToastSubscriber
import com.base.app.http.transformer.BaseTransformer
import com.base.app.http.transformer.TokenTransformer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun <T> Observable<T>.result(block: (T) -> Unit) = apply {
    this.subscribeOn(Schedulers.io())
        .retryWhen(RetryConfig.RetryWithDelay())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : ToastSubscriber<T>() {
            override fun onNext(t: T) {
                block.invoke(t)
            }
        })
}


fun <T> Observable<T>.resultDialog(activity: AppCompatActivity?, block: (T) -> Unit) = apply {
    this.subscribeOn(Schedulers.io())
        .retryWhen(RetryConfig.RetryWithDelay())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : DialogSubscriber<T>(activity) {
            override fun onNext(t: T) {
                block.invoke(t)
            }
        })
}

fun Observable<Result<String>>.success(block: (String) -> Unit) = apply {
    this.compose(TokenTransformer()).also { it.result(block) }
}

fun Observable<Result<String>>.successNoToken(block: (String) -> Unit) = apply {
    this.compose(BaseTransformer()).also { it.result(block) }
}


fun <T : BaseBean> Observable<Result<BaseListBean<T>>>.list(activity: AppCompatActivity? = null, block: (BaseListBean<T>) -> Unit) = apply {
    this.compose(TokenTransformer<Result<BaseListBean<T>>, BaseListBean<T>>())
        .also { observable -> if (activity != null) observable.resultDialog(activity) { result -> block.invoke(result) } else observable.result { block.invoke(it) } }
}

fun <T : BaseBean> Observable<Result<T>>.success(activity: AppCompatActivity? = null, block: (T) -> Unit) = apply {
    this.compose(TokenTransformer<Result<T>, T>())
        .also { observable -> if (activity != null) observable.resultDialog(activity) { result -> block.invoke(result) } else observable.result { block.invoke(it) } }
}


fun <T : BaseBean> Observable<Result<T>>.successNoTokenCheck(activity: AppCompatActivity? = null, block: (T) -> Unit) = apply {
    this.compose(BaseTransformer<Result<T>, T>()).also { observable -> if (activity != null) observable.resultDialog(activity) { result -> block.invoke(result) } else observable.result { block.invoke(it) } }
}


fun <T : BaseBean> Observable<Result<BaseListBean<T>>>.listNoCheckToken(activity: AppCompatActivity? = null, block: (BaseListBean<T>) -> Unit) = apply {
    this.compose(BaseTransformer<Result<BaseListBean<T>>, BaseListBean<T>>())
        .also { observable -> if (activity != null) observable.resultDialog(activity) { result -> block.invoke(result) } else observable.result { block.invoke(it) } }
}





