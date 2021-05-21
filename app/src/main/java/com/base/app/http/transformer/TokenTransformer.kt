package com.base.app.http.transformer

import com.base.app.http.HttpResultCode
import com.base.app.http.Result
import com.base.app.http.TokenInvalidHandler
import com.base.app.http.exception.TokenException
import io.reactivex.Observable
import io.reactivex.ObservableSource

class TokenTransformer<IN : Result<OUT>, OUT> : BaseTransformer<IN, OUT>() {

    override fun apply(observable: Observable<IN>): ObservableSource<OUT> {
        return observable.map { t ->
            if (t.code == HttpResultCode.TOKEN_INVALID) {
                TokenInvalidHandler.error()
                throw TokenException(t.message, t.code)
            }
            t.data
        }
    }

}