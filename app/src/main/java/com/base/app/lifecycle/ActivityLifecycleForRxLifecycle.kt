package com.base.app.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.blankj.utilcode.util.KeyboardUtils
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.subjects.Subject

class ActivityLifecycleForRxLifecycle : Application.ActivityLifecycleCallbacks {

    private val mFragmentLifecycle: FragmentManager.FragmentLifecycleCallbacks by lazy { FragmentLifecycleForRxLifecycle() }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        if (activity is ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.CREATE)
            if (activity is FragmentActivity) {
                activity.supportFragmentManager.registerFragmentLifecycleCallbacks(mFragmentLifecycle, true)
            }
        }
    }

    override fun onActivityStarted(activity: Activity) {
        if (activity is ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.START)
        }
    }


    override fun onActivityResumed(activity: Activity) {
        if (activity is ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.RESUME)
        }
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity is ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.PAUSE)
        }
    }


    override fun onActivityStopped(activity: Activity) {
        if (activity is ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.STOP)
        }
    }


    override fun onActivityDestroyed(activity: Activity) {
        if (activity is ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.DESTROY)
        }
        KeyboardUtils.fixSoftInputLeaks(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }


    private fun obtainSubject(activity: Activity): Subject<ActivityEvent> {
        return (activity as ActivityLifecycleable).provideLifecycleSubject()
    }

}