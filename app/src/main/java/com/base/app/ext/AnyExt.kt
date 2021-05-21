package com.base.app.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


fun Boolean.ifTrue(action: (Boolean) -> Unit): Boolean {
    if (this) {
        action.invoke(this)
    }
    return this
}


fun Boolean.ifFalse(action: (Boolean) -> Unit): Boolean {
    if (!this) {
        action.invoke(this)
    }
    return this
}


fun Boolean.trueIf(callback: () -> Unit) = apply {
    if (this) {
        callback.invoke()
    }
}

fun Boolean.falseIf(callback: () -> Unit) = apply {
    if (!this) {
        callback.invoke()
    }
}


@SuppressLint("SimpleDateFormat")
fun nowTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return sdf.format(Date())
}