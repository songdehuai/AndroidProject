package com.base.app.http.subscriber

import androidx.appcompat.app.AppCompatActivity
import com.base.app.ext.tip
import com.base.app.global.TipType
import com.base.app.http.exception.ResponseException


import java.lang.ref.WeakReference


abstract class DialogSubscriber<T>(private var mContext: AppCompatActivity?) : BaseSubscriber<T>() {

    private val contextRef: WeakReference<AppCompatActivity> by lazy { WeakReference(mContext) }

    override fun onError(e: ResponseException) {
        e.message.tip(TipType.ERROR, true)
    }

}