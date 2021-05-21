package com.base.app.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.base.app.R


fun ImageView.load(url: String) {
    Glide.with(this).load(url).into(this)
}

fun ImageView.loadCircle(url: String) {
    Glide.with(this).load(url).apply(RequestOptions.bitmapTransform(CircleCrop())).into(this)
}

fun ImageView.loadError(url: String, errorImg: Int = R.drawable.ic_head_default) {
    Glide.with(this).load(url).error(errorImg).into(this)
}

fun ImageView.loadCircleError(url: String, errorImg: Int = R.drawable.ic_head_default) {
    Glide.with(this).load(url).error(errorImg).apply(RequestOptions.bitmapTransform(CircleCrop())).into(this)
}


