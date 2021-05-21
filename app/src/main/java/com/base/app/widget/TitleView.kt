package com.base.app.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.base.app.R
import com.base.app.ext.*


class TitleView : ConstraintLayout {

    private val defaultHeight by lazy { 48f.dp2px(context) }
    private val leftView by lazy { findViewById<ImageView>(R.id.iv_left) }
    private val rightText by lazy { findViewById<TextView>(R.id.tv_right) }
    private val rightImg by lazy { findViewById<ImageView>(R.id.iv_right) }
    private val titleTextView by lazy { findViewById<TextView>(R.id.tv_center) }
    private val clTitleRoot by lazy { findViewById<ConstraintLayout>(R.id.cl_title_root) }
    private var leftClick: (() -> Unit)? = null
    private var rightClick: (() -> Unit)? = null

    private var titleText = ""
    private var titleMode = 0


    constructor(context: Context?) : super(context!!) {
        initViews()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        initViews()
        initAttr(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        initViews()
        initAttr(attrs)
    }


    private fun initViews() {
        View.inflate(context, R.layout.view_title, this)
        leftView.setOnClickListener {
            (leftClick != null).ifTrue {
                leftClick?.invoke()
            }.ifFalse {
                finishActivity()
            }
        }
        rightText.setOnClickListener { rightClick?.invoke() }
    }

    @SuppressLint("Recycle")
    private fun initAttr(attrs: AttributeSet?) {
        val obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TitleView)
        titleText = obtainStyledAttributes.getString(R.styleable.TitleView_title).toString()
        titleMode = obtainStyledAttributes.getInt(R.styleable.TitleView_titleMode, 0)
        setMode(titleMode)
        setTitle(titleText)
        obtainStyledAttributes.recycle()

    }

    private fun finishActivity() {
        if (context is Activity) {
            (context as Activity).finish()
        }
    }

    fun setTitleColor(color: Int) = apply {
        clTitleRoot.setBackgroundResource(color)
    }


    fun setTitle(title: String) = apply {
        titleTextView.setText(title)
    }

    fun setRightTitle(title: String) = apply {
        rightText.setText(title)
    }

    fun leftClick(action: (() -> Unit)) = apply {
        leftClick = action
    }

    fun rightClick(action: (() -> Unit)) = apply {
        rightClick = action
    }

    fun setTitleBarColor(color: Int) {
        setBackgroundColor(color)
    }


    private fun setMode(titleMode: Int) {
        when (titleMode) {
            0 -> {
                setMode(TitleMode.TEXT)
            }
            1 -> {
                setMode(TitleMode.IMG)
            }
            2 -> {
                setMode(TitleMode.NONE)
            }
        }
    }

    fun setMode(mode: TitleMode) = apply {
        when (mode) {
            TitleMode.IMG -> {
                rightImg.VISIBLE()
                rightText.INVISIBLE()
                titleMode = 1
            }
            TitleMode.TEXT -> {
                rightImg.INVISIBLE()
                rightText.VISIBLE()
                titleMode = 0
            }
            TitleMode.NONE -> {
                rightImg.INVISIBLE()
                rightText.INVISIBLE()
                titleMode = 2
            }
        }
    }


    enum class TitleMode {
        IMG, TEXT, NONE
    }
}