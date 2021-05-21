package com.base.app.http.transformer

import com.base.app.http.Result
import com.base.app.http.exception.ResponseException
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.exceptions.Exceptions


open class BaseTransformer<T : Result<R>, R> : ObservableTransformer<T, R> {

    override fun apply(observable: Observable<T>): ObservableSource<R> {
        return observable.map { t ->
            var msg: String? = null
            if (!t.isSuccess() || t.data == null) {
                msg = t.message
            }
            msg?.let {
                try {
                    throw ResponseException(it, t.code)
                } catch (e: ResponseException) {
                    e.printStackTrace()
                    throw Exceptions.propagate(e)
                }
            }
            t.data
        }
    }

}