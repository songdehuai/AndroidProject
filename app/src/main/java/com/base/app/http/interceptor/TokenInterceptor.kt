package com.base.app.http.interceptor

import android.util.Log
import com.base.app.http.TokenManager

import okhttp3.*


class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = TokenManager.getToken()
        val request = chain.request().newBuilder().addHeader("token", token).build()
        Log.d("TOKEN", token)
        return chain.proceed(request)
    }

}