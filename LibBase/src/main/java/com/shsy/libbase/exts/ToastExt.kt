package com.shsy.libbase.exts

import android.widget.Toast

/**
 * @ClassName ToastUtil
 * @Description Toast 扩展类
 * @Author 嘟嘟侠
 * @Date 2021/10/29 9:13 上午
 * @Version 1.0
 */
fun Any.showToast() {
    Toast.makeText(appContext, if (this is String) this else this.toString(), Toast.LENGTH_SHORT)
        .show()
}