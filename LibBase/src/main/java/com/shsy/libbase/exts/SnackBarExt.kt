package com.shsy.libbase.exts

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * @ClassName SnackBarExt
 * @Description
 * @Author 嘟嘟侠
 * @Date 2022/1/13 9:10 上午
 * @Version 1.0
 */
fun Any.showSnackBar(v: View) {
    Snackbar.make(v, if (this is String) this else this.toString(), Snackbar.LENGTH_SHORT).show()
}