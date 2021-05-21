package com.base.app.ext

import android.text.*
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import com.tencent.mmkv.MMKV


fun String.getColorText(color: Int): CharSequence {
    val style = SpannableStringBuilder(this)
    style.setSpan(ForegroundColorSpan(color), 0, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return style
}


fun String.getSizeText(dp: Int): CharSequence {
    val ss = SpannableString(this)
    val ass = AbsoluteSizeSpan(dp, true)
    ss.setSpan(ass, 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return SpannedString(ss)
}

fun String.toSafePhone(): String {
    if (this.length >= 7) {
        val temp = substring(3, 7)
        val newPhone = this.replaceFirst(temp, "****")
        return newPhone
    }
    return this
}

private val mmkv by lazy { MMKV.mmkvWithID("EXT") }

fun String.save(key: String) {
    mmkv?.putString(key, this)
}

fun getSave(key: String, default: String): String {
    return mmkv?.getString(key, default)!!
}
