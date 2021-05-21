package com.base.app.http


object TokenInvalidHandler {

    private val tokenErrorHandler = arrayListOf<() -> Unit>()

    fun handler(block: () -> Unit) {
        tokenErrorHandler.add(block)
    }

    fun error() {
        tokenErrorHandler.forEach { it() }
    }
}