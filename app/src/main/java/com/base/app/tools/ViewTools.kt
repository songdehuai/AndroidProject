package com.base.app.tools

import android.content.Context
import android.content.res.Resources

object ViewTools {
    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        val resources: Resources = context.resources
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }
}