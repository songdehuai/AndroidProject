package com.base.app.http.exception

import java.io.IOException

open class ResponseException : IOException {

    private var errorCode: Int = 200

    constructor(message: String?, code: Int) : super(message) {
        errorCode = code
    }

    constructor() : super()

}