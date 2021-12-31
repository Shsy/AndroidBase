package com.shsy.libbase.widget

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.shsy.libbase.R
import com.shsy.libbase.exts.doClick
import com.shsy.libbase.utils.DisplayUtil

/**
 * @ClassName HeaderBar
 * @Description 头导航
 * @Author 嘟嘟侠
 * @Date 2021/11/10 1:57 下午
 * @Version 1.0
 */
class HeaderBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var mBackClickMethod: (() -> Boolean)? = null

    private val backImageView = ImageView(context).apply {
        layoutParams =
            LayoutParams(DisplayUtil.dp2px(40F), DisplayUtil.dp2px(40F)).apply {
                gravity = Gravity.CENTER_VERTICAL
                marginStart = DisplayUtil.dp2px(10F)
            }
        setPadding(DisplayUtil.dp2px(10F))
        setImageResource(R.mipmap.icon_header_bar_back)
    }
    private val titleTextView = TextView(context).apply {
        layoutParams =
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
            }
        textSize = 17F
        setTextColor(Color.parseColor("#333333"))
    }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar)
        val showBack = ta.getBoolean(R.styleable.HeaderBar_showBack, true)
        val titleText = ta.getString(R.styleable.HeaderBar_titleText)
        val rightText = ta.getString(R.styleable.HeaderBar_rightText)
        val rightImg = ta.getResourceId(R.styleable.HeaderBar_rightImg, -1)
        ta.recycle()

        layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(40F))

        if (tag == "transparent") {
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        } else {
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
        }

        if (showBack) {
            backImageView.doClick {
                val clickMethod = mBackClickMethod
                if (!(clickMethod != null && clickMethod.invoke())) {
                    if (context is Activity) {
                        context.finish()
                    }
                }
            }
            addView(backImageView)
        }

        titleTextView.text = titleText
        addView(titleTextView)
    }

    /**
     * method返回boolean
     * 如果返回true 下层处理逻辑
     * 如果返回false 上层处理逻辑
     */
    fun setBackClickMethod(method: () -> Boolean) {
        mBackClickMethod = method
    }

    fun setTitleText(titleText: CharSequence) {
        titleTextView.text = titleText
    }

    fun getTitleText(): String {
        return titleTextView.text.toString()
    }
}