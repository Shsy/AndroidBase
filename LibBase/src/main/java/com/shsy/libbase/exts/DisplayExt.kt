package com.shsy.libbase.exts

/**
 * @ClassName DisplayExt
 * @Description DP 扩展
 * @Author 嘟嘟侠
 * @Date 2021/11/2 10:49 上午
 * @Version 1.0
 */
val Int.dp2px: Int
    get() {
        val scale = appContext.resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }

val Float.dp2px: Int
    get() {
        val scale = appContext.resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }

val Int.sp2px: Int
    get() {
        val fontScale = appContext.resources.displayMetrics.scaledDensity
        return (this * fontScale + 0.5f).toInt()
    }

val Float.sp2px: Int
    get() {
        val fontScale = appContext.resources.displayMetrics.scaledDensity
        return (this * fontScale + 0.5f).toInt()
    }