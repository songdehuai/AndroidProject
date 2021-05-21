package com.base.app.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.flexbox.FlexWrap

import com.google.android.flexbox.FlexboxLayout
import com.base.app.R
import com.base.app.ext.dp2px

/**
 * 标签View
 * @author songdehuai
 */
class SingleChoiceView : FlexboxLayout {


    private val tagList = ArrayList<String>()
    private var tagHeight = 0
    private var viewList = ArrayList<TextView>()
    private val mPadding by lazy { 14f.dp2px(context) }
    private val mMargins by lazy { 8f.dp2px(context) }
    private val mPaddingTop by lazy { 4f.dp2px(context) }
    private val mPaddingBottom by lazy { 2f.dp2px(context) }

    private var select: Int? = null

    constructor(context: Context) : super(context) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initViews()
    }

    private fun initViews() {
        flexWrap = FlexWrap.WRAP
    }

    private fun createTag(tag: String, id: Int): View {
        val textView = TextView(context)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, mMargins, 0)
        textView.setPadding(mPadding, mPaddingTop, mPadding, mPaddingBottom)
        textView.layoutParams = layoutParams
        textView.text = tag
        textView.background = ContextCompat.getDrawable(context, R.drawable.bg_order_tag_no)
        textView.gravity = Gravity.CENTER
        textView.textSize = 14f
        textView.setTextColor(Color.parseColor("#73000000"))
        textView.id = id
        textView.setOnClickListener {
            select(id)
        }
        tagHeight = textView.height
        viewList.add(textView)
        return textView
    }

    private fun select(id: Int) {
        viewList.filter { it.id != id }.forEach {
            it.background = ContextCompat.getDrawable(context, R.drawable.bg_order_tag_no)
            it.setTextColor(Color.parseColor("#73000000"))
        }
        viewList[id].let {
            it.background = ContextCompat.getDrawable(context, R.drawable.bg_order_tag)
            it.setTextColor(ContextCompat.getColor(context, R.color.main))
        }
        select = id
    }

    fun clear() {
        viewList.clear()
        tagList.clear()
        removeAllViews()
    }

    fun addTags(tag: List<String>) {
        clear()
        tagList.addAll(tag)
        tagList.forEachIndexed { index, s ->
            addView(createTag(s, index))
        }
    }

    fun getSelect(): Int? {
        return select
    }

}
