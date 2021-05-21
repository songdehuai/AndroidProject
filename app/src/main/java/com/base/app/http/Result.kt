package com.base.app.http

data class Result<T>(var code: Int, var message: String?, var data: T?) {

    fun isSuccess(): Boolean {
        return code == HttpResultCode.SUCCESS
    }

    fun isError(): Boolean {
        return code != HttpResultCode.SUCCESS || data == null
    }

    fun success(block: (Boolean) -> Unit) = apply {
        isSuccess().also {
            if (it) {
                block.invoke(it)
            }
        }
    }

    fun successData(block: (Boolean) -> Unit) = apply {
        isSuccess().also {
            if (it && data != null) {
                block.invoke(it)
            }
        }
    }


    fun error(block: (Boolean) -> Unit) = apply {
        isError().also {
            if (it) {
                block.invoke(it)
            }
        }
    }
}
