package com.base.app.base

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.base.app.App
import com.base.app.R
import com.base.app.ext.autoNightMode
import com.base.app.ext.ui
import com.base.app.lifecycle.ActivityLifecycleable
import com.base.app.lifecycle.RxLifecycleUtils
import com.base.app.tools.StatusBarUtil
import com.base.app.tools.dialog.LoadDialog
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

open class BaseActivity : AppCompatActivity(), ActivityLifecycleable {

    lateinit var thisActivity: BaseActivity

    private var loadingDialog: LoadDialog? = null

    private val mLifecycleSubject: BehaviorSubject<ActivityEvent> by lazy { BehaviorSubject.create<ActivityEvent>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thisActivity = this
        StatusBarUtil.setTranslucent(this)
        autoNightMode()
        autoPadding()
        initDialog()

    }

    private fun initDialog() {
        loadingDialog = LoadDialog(this)
    }

    fun autoPadding() {
        if (isAutoPadding()) {
            val titleView = findViewById<View>(R.id.title_view_id)
            if (titleView != null) {
                titleView.setPadding(0, App.statusHeight, 0, 0)
            }
        }
    }

    open fun isAutoPadding(): Boolean {
        return true
    }

    open fun nightMode() {

    }

    open fun lightMode() {

    }

    open fun nightModeChanged(isNight: Boolean) {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (autoNightMode()) {
            nightMode()
            nightModeChanged(true)
        } else {
            lightMode()
            nightModeChanged(false)
        }
    }

    fun showLoad() {
        ui { loadingDialog?.show() }
    }

    fun hideLoad() {
        ui { loadingDialog?.dismiss() }
    }


    fun <T> loading(): ObservableTransformer<T, T> {
        return ObservableTransformer { observer ->
            observer.doOnSubscribe { showLoad() }.doFinally { hideLoad() }
        }
    }

    fun <T> lift(): ObservableTransformer<T, T> {
        return RxLifecycleUtils.bindUntilEvent(this, ActivityEvent.DESTROY)
    }


    override fun provideLifecycleSubject(): Subject<ActivityEvent> {
        return mLifecycleSubject
    }

    override fun onDestroy() {
        super.onDestroy()
        hideLoad()
    }
}