package com.base.app.lifecycle

import com.base.app.lifecycle.Lifecycleable
import com.trello.rxlifecycle2.android.FragmentEvent


interface FragmentLifecycleable : Lifecycleable<FragmentEvent>