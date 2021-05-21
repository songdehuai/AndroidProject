package com.base.app.ext

import com.orhanobut.logger.Logger



fun Any?.logI() {
    this?.let {
        Logger.i("logI", it)
    }
}

fun Any?.logD() {
    this?.let {
        Logger.d("logD",it)
    }
}

fun Any?.logW() {
    this?.let {
        Logger.e("logW", it)
    }
}

fun Any?.logE() {
    this?.let {
        Logger.e("logE", it)
    }
}

fun Any?.logWtf() {
    this?.let {
        Logger.wtf("wtf!", it)
    }
}