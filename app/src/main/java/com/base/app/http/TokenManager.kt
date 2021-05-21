package com.base.app.http

import com.tencent.mmkv.MMKV

object TokenManager {

    private const val TOKEN_KEY = "TOKEN_KEY"

    private val mmkv = MMKV.mmkvWithID(TOKEN_KEY)

    fun saveToken(token: String) {
        mmkv?.encode(TOKEN_KEY, token)
    }

    fun getToken(): String {
        return mmkv?.decodeString(TOKEN_KEY, "") ?: ""
    }

    fun clearToken() {
        mmkv?.clear()
    }
}