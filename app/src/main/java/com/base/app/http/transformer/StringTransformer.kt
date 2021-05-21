package com.base.app.http.transformer

import com.base.app.http.Result
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer


open class StringTransformer<T : Result<String>> : ObservableTransformer<T, String> {

    override fun apply(upstream: Observable<T>): ObservableSource<String> {
        return upstream.map { it.data }
    }

}