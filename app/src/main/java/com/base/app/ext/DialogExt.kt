package com.base.app.ext

import android.app.AlertDialog
import android.os.Build
import android.view.Gravity
import android.view.WindowManager


fun androidx.appcompat.app.AlertDialog.Builder.fixShow() = apply {
    this.create().fixShow()
}

fun AlertDialog.Builder.fixShow() = apply {
    this.create().fixShow()
}


fun androidx.appcompat.app.AlertDialog.fixShow() = apply {
    this.show()
    this.window?.run {
        attributes = attributes.apply {
            width = (getWidth() * 0.95).toInt()
            gravity = Gravity.CENTER
        }
    }
}

fun AlertDialog.fixShow() = apply {
    this.show()
    this.window?.run {
        attributes = attributes.apply {
            width = (getWidth() * 0.95).toInt()
            gravity = Gravity.CENTER
        }
    }
}