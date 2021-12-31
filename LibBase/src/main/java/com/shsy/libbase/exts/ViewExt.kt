package com.shsy.libbase.exts

import android.view.View
import android.view.animation.AlphaAnimation
import androidx.core.view.isGone
import androidx.core.view.isVisible

/**
 * @ClassName ViewExt
 * @Description View 扩展类
 * @Author 嘟嘟侠
 * @Date 2021/10/29 9:40 上午
 * @Version 1.0
 */
fun View.doClick(m: () -> Unit) {
    this.setOnClickListener { m.invoke() }
}

fun View.doSingleClick(m: () -> Unit) {
    var time = 0L
    this.setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (currentTime - time > 2000) {
            time = currentTime
            m.invoke()
        }
    }
}

fun View.doLongClick(m: () -> Boolean) {
    this.setOnLongClickListener { m.invoke() }
}

fun View.visible() {
    this.isVisible = true
}

/**
 * 显示view，带有渐显动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View.visibleAlphaAnimation(duration: Long = 500L) {
    visible()
    this.startAnimation(AlphaAnimation(0f, 1f).apply {
        this.duration = duration
        fillAfter = true
    })
}

fun View.gone() {
    this.isGone = true
}

/**
 * 隐藏view，带有渐隐动画效果。
 *
 * @param duration 毫秒，动画持续时长，默认500毫秒。
 */
fun View.goneAlphaAnimation(duration: Long = 500L) {
    gone()
    this.startAnimation(AlphaAnimation(1f, 0f).apply {
        this.duration = duration
        fillAfter = true
    })
}