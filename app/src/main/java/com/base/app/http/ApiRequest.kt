package com.base.app.http

import com.base.app.BuildConfig
import com.base.app.global.ServerUrl
import com.base.app.global.Api
import com.base.app.http.gson.factory.GsonFactory
import com.base.app.http.interceptor.TokenInterceptor
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


fun api(): Api {
    return ApiRequest.request
}

object ApiRequest {

    private val log by lazy {
        HttpLoggingInterceptor().also { it.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE }
    }

    private val client = OkHttpClient
        .Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(TokenInterceptor())
        .addInterceptor(log)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(ServerUrl.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonFactory.getSingletonGson()))
            .build()
    }

    val request by lazy { retrofit.create(Api::class.java) }

}