package com.base.app.http.subscriber

import com.base.app.ext.tipError
import com.base.app.ext.toast
import com.base.app.http.exception.ResponseException


abstract class ToastSubscriber<T> : BaseSubscriber<T>() {

    override fun onError(e: ResponseException) {
        e.message?.tipError()
    }



}
