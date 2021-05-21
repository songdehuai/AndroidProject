package com.base.app.http.subscriber

import com.base.app.http.HttpResultCode
import com.base.app.http.exception.ResponseException
import com.base.app.http.exception.TokenException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


abstract class BaseSubscriber<T> : Observer<T> {

    override fun onError(e: Throwable) {
        e.printStackTrace()
        when {
            e.cause is ResponseException -> {
                onError(e.cause as ResponseException)
            }
            e is TokenException -> {
                val tokenException = TokenException(e.message, HttpResultCode.TOKEN_INVALID)
                onError(tokenException)
            }
            else -> {
                val exception = ResponseException("服务器异常", HttpResultCode.EXE_ERROR)
                onError(exception)
            }
        }
    }

    override fun onSubscribe(d: Disposable) {}

    abstract fun onError(e: ResponseException)

    override fun onComplete() {}

}