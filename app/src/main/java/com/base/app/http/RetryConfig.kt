package com.base.app.http


import com.base.app.BuildConfig
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

object RetryConfig {
    class RetryWithDelay(private val maxRetries: Int = 3, private val delaySeconds: Long = 5L) :
        Function<Observable<in Throwable>, Observable<*>> {
        private var retryCount = 0
        override fun apply(observable: Observable<in Throwable>): Observable<*> = observable
            .flatMap { throwable ->
                if (++retryCount < maxRetries && !BuildConfig.DEBUG) {
                    Observable.timer(delaySeconds, TimeUnit.SECONDS)
                } else {
                    Observable.error(throwable as Throwable)
                }
            }
    }
}
