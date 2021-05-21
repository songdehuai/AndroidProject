package com.base.app.http.transformer

import com.base.app.http.Result
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer


open class AnyTransformer<T : Result<Any>> : ObservableTransformer<T, Any> {

    override fun apply(upstream: Observable<T>): ObservableSource<Any> {
        return upstream.map { it.data }
    }

}