package com.base.app.ext

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private val handler = Handler(Looper.getMainLooper())
private val coreSize = Runtime.getRuntime().availableProcessors() + 1

private val http: ExecutorService = Executors.newFixedThreadPool(coreSize)
private val single: ExecutorService = Executors.newSingleThreadExecutor()

fun <T> T.ui(block: T.() -> Unit) {
    handler.post {
        try {
            block()
        } catch (e: Exception) {
            e.message.logE()
        }
    }
}


fun <T> T.delay(delayMillis: Long, block: T.() -> Unit) {
    handler.postDelayed({
        try {
            block()
        } catch (e: Exception) {
            e.message.logE()
        }
    }, delayMillis)
}


fun <T> T.task(block: T.() -> Unit) {
    single.execute {
        try {
            block()
        } catch (e: Exception) {
            e.message.logE()
        }
    }
}

fun <T> T.httpTask(block: T.() -> Unit) {
    http.execute {
        try {
            block()
        } catch (e: Exception) {
            e.message.logE()
        }
    }
}
