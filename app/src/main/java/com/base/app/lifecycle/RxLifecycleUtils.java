package com.base.app.lifecycle;


import com.base.app.lifecycle.ActivityLifecycleable;
import com.base.app.lifecycle.FragmentLifecycleable;
import com.base.app.lifecycle.Lifecycleable;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.annotations.NonNull;


public class RxLifecycleUtils {

    private RxLifecycleUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static <T, R> LifecycleTransformer<T> bindUntilEvent(@NonNull final Lifecycleable<R> lifecycleable, final R event) {
        return RxLifecycle.bindUntilEvent(lifecycleable.provideLifecycleSubject(), event);
    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull Lifecycleable lifecycleable) {
        if (lifecycleable instanceof ActivityLifecycleable) {
            return RxLifecycleAndroid.bindActivity(((ActivityLifecycleable) lifecycleable).provideLifecycleSubject());
        } else if (lifecycleable instanceof FragmentLifecycleable) {
            return RxLifecycleAndroid.bindFragment(((FragmentLifecycleable) lifecycleable).provideLifecycleSubject());
        } else {
            throw new IllegalArgumentException("Lifecycleable not match");
        }
    }

}
