package com.base.app.ext

import android.graphics.Color
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.base.app.R
import com.base.app.global.TipType


fun Any?.toast() {
    this.tip(TipType.SUCCESS)
}


fun Any?.tipSuccess() {
    this.tip(TipType.SUCCESS)
}

fun Any?.tipError() {
    this.tip(TipType.ERROR)
}


fun Any?.tipWarning() {
    this.tip(TipType.WARNING)
}


fun Any?.tipWtf() {
    this.tip(TipType.WTF)
}


fun Any?.tip(type: TipType, isIcon: Boolean = false) {
    if (this.toString().isEmpty()) {
        return
    }
    var color = successColor
    var topIcon = R.drawable.ic_success

    when (type) {
        TipType.SUCCESS -> {
            color = successColor
            topIcon = R.drawable.ic_success
        }
        TipType.ERROR -> {
            color = errorColor
            topIcon = R.drawable.ic_error
        }
        TipType.WARNING -> {
            color = warningColor
            topIcon = R.drawable.ic_warning
        }
        TipType.WTF -> {
            color = wtfColor
            topIcon = R.drawable.ic_wtf
        }
    }
    if (isIcon) {
        ToastUtils.make().setBgColor(color).setTopIcon(topIcon).setTextColor(Color.WHITE).setGravity(Gravity.CENTER, 0, 0).show(this.toString())
    } else {
        ToastUtils.make().setBgColor(color).setTextColor(Color.WHITE).setGravity(Gravity.CENTER, 0, 0).show(this.toString())
    }

}




