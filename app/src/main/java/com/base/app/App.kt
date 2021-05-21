package com.base.app

import android.app.Application
import com.base.app.ext.logE
import com.base.app.http.TokenInvalidHandler
import com.base.app.lifecycle.ActivityLifecycleForRxLifecycle
import com.base.app.tools.ViewTools
import com.blankj.utilcode.util.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.mmkv.MMKV
import com.your.app.MainActivity
import com.zxy.recovery.core.Recovery
import io.reactivex.plugins.RxJavaPlugins


class App : Application() {

    companion object {

        lateinit var app: App
        val statusHeight by lazy { ViewTools.getStatusBarHeight(app) }
    }

    private val activityRxLifeCallback = ActivityLifecycleForRxLifecycle()

    override fun onCreate() {
        super.onCreate()
        app = this
        MMKV.initialize(this)
        init()
        initDevelop()
        Utils.init(this)
    }

    private fun init() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(3)
            .methodOffset(7)
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        RxJavaPlugins.setErrorHandler {
            it.message.logE()
        }

        TokenInvalidHandler.handler {

        }
        registerActivityLifecycleCallbacks(activityRxLifeCallback)
    }

    private fun initDevelop() {
        Recovery.getInstance()
            .debug(BuildConfig.DEBUG)
            .recoverInBackground(true)
            .recoverStack(true)
            .mainPage(MainActivity::class.java)
            .recoverEnabled(true)
            .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
            .init(this)
    }
}