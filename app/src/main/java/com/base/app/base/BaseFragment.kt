package com.base.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {


    lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getLayout(container).let {
            if (it is View) {
                mView = it
            }
            if (it is Int) {
                mView = View.inflate(context, it, null)
            }
        }
        init(savedInstanceState)
        return mView
    }

    abstract fun getLayout(container: ViewGroup?): Any


    abstract fun init(savedInstanceState: Bundle?)

}