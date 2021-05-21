package com.base.app.lifecycle

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.subjects.Subject


class FragmentLifecycleForRxLifecycle : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentAttached(fragmentManager: FragmentManager, f: Fragment, context: Context) {
        super.onFragmentAttached(fragmentManager, f, context)
        f.let {
            if (it is FragmentLifecycleable) {
                obtainSubject(it).onNext(FragmentEvent.ATTACH)
            }
        }
    }

    override fun onFragmentCreated(fragmentManager: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        super.onFragmentCreated(fragmentManager, f, savedInstanceState)
        f.let {
            if (it is FragmentLifecycleable) {
                obtainSubject(it).onNext(FragmentEvent.CREATE)
            }
        }
    }

    override fun onFragmentViewCreated(fragmentManager: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        super.onFragmentViewCreated(fragmentManager, f, v, savedInstanceState)
        f.let {
            if (it is FragmentLifecycleable) {
                obtainSubject(it).onNext(FragmentEvent.CREATE_VIEW)
            }
        }
    }

    override fun onFragmentStarted(fragmentManager: FragmentManager, f: Fragment) {
        super.onFragmentStarted(fragmentManager, f)
        f.let {
            if (it is FragmentLifecycleable) {
                obtainSubject(it).onNext(FragmentEvent.START)
            }
        }
    }

    override fun onFragmentResumed(fragmentManager: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fragmentManager, f)
        f.let {
            if (it is FragmentLifecycleable) {
                obtainSubject(it).onNext(FragmentEvent.RESUME)
            }
        }
    }

    override fun onFragmentPaused(fragmentManager: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fragmentManager, f)
        f.let {
            if (it is FragmentLifecycleable) {
                obtainSubject(it).onNext(FragmentEvent.PAUSE)
            }
        }
    }

    override fun onFragmentStopped(fragmentManager: FragmentManager, f: Fragment) {
        super.onFragmentStopped(fragmentManager, f)
        f.let {
            if (it is FragmentLifecycleable) {
                obtainSubject(it).onNext(FragmentEvent.STOP)
            }
        }
    }

    override fun onFragmentViewDestroyed(fragmentManager: FragmentManager, f: Fragment) {
        super.onFragmentViewDestroyed(fragmentManager, f)
        f.let {
            if (it is FragmentLifecycleable) {
                obtainSubject(it).onNext(FragmentEvent.DESTROY_VIEW)
            }
        }
    }

    override fun onFragmentDestroyed(fragmentManager: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fragmentManager, f)
        f.let {
            if (it is FragmentLifecycleable) {
                obtainSubject(it).onNext(FragmentEvent.DESTROY)
            }
        }
    }

    override fun onFragmentDetached(fragmentManager: FragmentManager, f: Fragment) {
        super.onFragmentDetached(fragmentManager, f)
        f.let {
            if (it is FragmentLifecycleable) {
                obtainSubject(it).onNext(FragmentEvent.DETACH)
            }
        }
    }

    private fun obtainSubject(fragment: Fragment): Subject<FragmentEvent> {
        return (fragment as FragmentLifecycleable).provideLifecycleSubject()
    }

}