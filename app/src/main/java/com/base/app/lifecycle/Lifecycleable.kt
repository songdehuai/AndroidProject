package com.base.app.lifecycle


import androidx.annotation.NonNull
import io.reactivex.subjects.Subject


interface Lifecycleable<T> {
    @NonNull
    fun provideLifecycleSubject(): Subject<T>
}