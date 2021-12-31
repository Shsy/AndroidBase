package com.shsy.libbase.exts

import android.util.Log
import com.shsy.libbase.BuildConfig

/**
 * @ClassName LogUtil
 * @Description Log 扩展类
 * @Author 嘟嘟侠
 * @Date 2021/10/29 9:11 上午
 * @Version 1.0
 */
const val TAG = "shsy_log"

private var showLog = BuildConfig.DEBUG

private enum class LEVEL {
    V, D, I, W, E
}

fun Any.logV(tag: String = TAG) = log(LEVEL.V, tag, if (this is String) this else this.toString())
fun Any.logD(tag: String = TAG) = log(LEVEL.D, tag, if (this is String) this else this.toString())
fun Any.logI(tag: String = TAG) = log(LEVEL.I, tag, if (this is String) this else this.toString())
fun Any.logW(tag: String = TAG) = log(LEVEL.W, tag, if (this is String) this else this.toString())
fun Any.logE(tag: String = TAG) = log(LEVEL.E, tag, if (this is String) this else this.toString())

private fun log(level: LEVEL, tag: String, message: String) {
    if (!showLog) return
    when (level) {
        LEVEL.V -> Log.v(tag, message)
        LEVEL.D -> Log.d(tag, message)
        LEVEL.I -> Log.i(tag, message)
        LEVEL.W -> Log.w(tag, message)
        LEVEL.E -> Log.e(tag, message)
    }
}