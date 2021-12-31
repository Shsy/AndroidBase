package com.shsy.libbase.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.shsy.libbase.exts.appContext

/**
 * @ClassName DisplayExt
 * @Description DP 工具
 * @Author 嘟嘟侠
 * @Date 2021/11/2 10:49 上午
 * @Version 1.0
 */
object DisplayUtil {
    /**
     * convert px to its equivalent dp
     *
     *
     * 将px转换为与之相等的dp
     */
    fun px2dp(pxValue: Float): Int {
        val scale: Float = appContext.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }


    /**
     * convert dp to its equivalent px
     *
     *
     * 将dp转换为与之相等的px
     */
    fun dp2px(dipValue: Float): Int {
        val scale: Float = appContext.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }


    /**
     * convert px to its equivalent sp
     *
     *
     * 将px转换为sp
     */
    fun px2sp(pxValue: Float): Int {
        val fontScale: Float = appContext.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }


    /**
     * convert sp to its equivalent px
     *
     *
     * 将sp转换为px
     */
    fun sp2px(spValue: Float): Int {
        val fontScale: Float = appContext.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    fun getScreenWidth(): Int {
        return appContext.resources.displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return appContext.resources.displayMetrics.heightPixels
    }
}