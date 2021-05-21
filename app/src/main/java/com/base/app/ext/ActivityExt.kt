package com.base.app.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Parcelable
import android.view.Gravity
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ServiceUtils
import com.base.app.R
import com.base.app.base.BaseActivity
import com.base.app.tools.StatusBarUtil


fun <T : Parcelable> Intent.check(key: String): T? {
    return if (this.hasExtra(key)) {
        this.getParcelableExtra<T>(key)
    } else {
        null
    }
}

fun Context.start(cls: Class<*>) {
    val intent = Intent(this, cls)
    this.startActivity(intent)
}


fun Context.startService(cls: Class<*>) {
    val intent = Intent(this, cls)
    ServiceUtils.startService(intent)
}

fun Context.stopService(cls: Class<*>) {
    val intent = Intent(this, cls)
    this.stopService(intent)
}

fun Context.startWithAnim(cls: Class<*>, animIn: Int = R.anim.anim_start_in, animOut: Int = R.anim.anim_start_out) {
    this.start(cls)
    if (this is Activity) {
        this.overridePendingTransition(animIn, animOut)
    }
}


fun Context.startNewTask(cls: Class<*>) {
    val intent = Intent(this, cls)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    this.startActivity(intent)
}


fun Fragment.start(cls: Class<*>) {
    val intent = Intent(this.context, cls)
    this.startActivity(intent)
}

/**
 * 是否开启暗黑模式
 */
fun Activity.isNightMode(): Boolean {
    val currentNightMode: Int = this.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK
    return currentNightMode == Configuration.UI_MODE_NIGHT_YES
}

fun Activity.autoNightMode(): Boolean {
    return this.isNightMode().also { night ->
        if (night) {
            this.setDarkMode()
            if (this is BaseActivity) {
                this.nightMode()
            }
        } else {
            this.setLightMode()
            if (this is BaseActivity) {
                this.lightMode()
            }
        }
    }
}

fun Activity.translucent() {
    StatusBarUtil.setTranslucent(this)
}

fun Activity.setLightMode() {
    StatusBarUtil.setLightMode(this)
}

fun Activity.setDarkMode() {
    StatusBarUtil.setDarkMode(this)
}

@RequiresApi(Build.VERSION_CODES.R)
fun Activity.dialogActivityMargin(margin: Float) {
    val params = this.window.attributes
    params.width = this.windowManager.currentWindowMetrics.bounds.right - margin.dp2px(this)
    params.gravity = Gravity.CENTER
    window.setBackgroundDrawableResource(R.color.white)
    window.attributes = params
}
