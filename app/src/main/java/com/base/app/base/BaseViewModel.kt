package com.base.app.base

import androidx.lifecycle.ViewModel
import com.base.app.http.api

open class BaseViewModel : ViewModel() {
    open val api by lazy { api() }
}