package com.shsy.libbase.exts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.io.Serializable

/**
 * @ClassName ActivityExt
 * @Description Act 扩展
 * @Author 嘟嘟侠
 * @Date 2021/11/9 10:11 上午
 * @Version 1.0
 */
inline fun <reified T : FragmentActivity> FragmentActivity.startActivityExt(
    flags: Int? = null,
    extra: Bundle? = null,
    value: Pair<String, Any>? = null,
    values: Collection<Pair<String, Any?>?>? = null
) {
    val list = ArrayList<Pair<String, Any?>?>()
    value?.let { list.add(it) }
    values?.let { list.addAll(it) }
    startActivity(getIntent<T>(flags, extra, list))
}

inline fun <reified T : FragmentActivity> Fragment.startActivityExt(
    flags: Int? = null,
    extra: Bundle? = null,
    value: Pair<String, Any>? = null,
    values: Collection<Pair<String, Any?>?>? = null
) =
    activity?.let {
        val list = ArrayList<Pair<String, Any?>?>()
        value?.let { v -> list.add(v) }
        values?.let { v -> list.addAll(v) }
        startActivity(it.getIntent<T>(flags, extra, list))
    }

inline fun <reified T : FragmentActivity> FragmentActivity.startActivityForResultExt(
    flags: Int? = null,
    extra: Bundle? = null,
    value: Pair<String, Any>? = null,
    values: Collection<Pair<String, Any?>?>? = null,
    noinline actResult: (result: ActivityResult) -> Unit
) {
    val list = ArrayList<Pair<String, Any?>?>()
    value?.let { list.add(it) }
    values?.let { list.addAll(it) }

    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        actResult(result)
    }.launch(
        getIntent<T>(flags, extra, list)
    )
}

inline fun <reified T : FragmentActivity> Fragment.startActivityForResultExt(
    flags: Int? = null,
    extra: Bundle? = null,
    value: Pair<String, Any>? = null,
    values: Collection<Pair<String, Any?>?>? = null,
    noinline actResult: (result: ActivityResult) -> Unit
) {
    val list = ArrayList<Pair<String, Any?>?>()
    value?.let { list.add(it) }
    values?.let { list.addAll(it) }

    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        actResult(result)
    }.launch(
        activity?.getIntent<T>(flags, extra, list)
    )
}

inline fun <reified T : Context> Context.getIntent(
    flags: Int? = null,
    extra: Bundle? = null,
    pairs: List<Pair<String, Any?>?>? = null
): Intent =
    Intent(this, T::class.java).apply {
        flags?.let { setFlags(flags) }
        extra?.let { putExtras(extra) }
        pairs?.let {
            for (pair in pairs)
                pair?.let {
                    val name = pair.first
                    when (val value = pair.second) {
                        is Int -> putExtra(name, value)
                        is Byte -> putExtra(name, value)
                        is Char -> putExtra(name, value)
                        is Short -> putExtra(name, value)
                        is Boolean -> putExtra(name, value)
                        is Long -> putExtra(name, value)
                        is Float -> putExtra(name, value)
                        is Double -> putExtra(name, value)
                        is String -> putExtra(name, value)
                        is CharSequence -> putExtra(name, value)
                        is Parcelable -> putExtra(name, value)
                        is Array<*> -> putExtra(name, value)
                        is ArrayList<*> -> putExtra(name, value)
                        is Serializable -> putExtra(name, value)
                        is BooleanArray -> putExtra(name, value)
                        is ByteArray -> putExtra(name, value)
                        is ShortArray -> putExtra(name, value)
                        is CharArray -> putExtra(name, value)
                        is IntArray -> putExtra(name, value)
                        is LongArray -> putExtra(name, value)
                        is FloatArray -> putExtra(name, value)
                        is DoubleArray -> putExtra(name, value)
                        is Bundle -> putExtra(name, value)
                        is Intent -> putExtra(name, value)
                        else -> {
                        }
                    }
                }
        }
    }

// 开启软键盘
fun FragmentActivity.showKeyBoard(v: View) {
    ContextCompat.getSystemService(applicationContext, InputMethodManager::class.java)
        ?.showSoftInput(v, 0)
}

// 关闭软键盘
fun FragmentActivity.hideKeyBoard(v: View) {
    ContextCompat.getSystemService(applicationContext, InputMethodManager::class.java)
        ?.hideSoftInputFromWindow(v.windowToken, 0)
}