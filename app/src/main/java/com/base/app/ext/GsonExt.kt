package com.base.app.ext

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val gson = Gson()

inline fun <reified T> Any.toBean(): T {
    val type = object : TypeToken<T>() {}.type
    return gson.fromJson(this.toString(), type)
}

fun Any.toJson(): String {
    return gson.toJson(this)
}
