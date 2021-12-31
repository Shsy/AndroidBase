package com.shsy.libbase.exts

import android.content.Context
import android.os.Build
import com.shsy.libbase.base.BaseApplication

/**
 * @ClassName AppExt
 * @Description App 扩展类
 * @Author 嘟嘟侠
 * @Date 2021/10/29 9:14 上午
 * @Version 1.0
 */
val appContext = BaseApplication.getContext()
val serverBaseUrl = BaseApplication.getServerBaseUrl()

fun Context.getVersionName(): String = packageManager.getPackageInfo(packageName, 0).versionName
fun Context.getVersionCode(): Long = with(packageManager.getPackageInfo(packageName, 0)) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) longVersionCode else versionCode.toLong()
}

fun Context.isPackageInstalled(pkgName: String) = try {
    packageManager.getPackageInfo(pkgName, 0)
    true
} catch (e: Exception) {
    false
}

fun Context.getCommonFileDirPath(type: String?): String {
    return (getExternalFilesDir(type) ?: filesDir).absolutePath
}

fun Context.getCommonCacheDirPath(): String {
    return (externalCacheDir ?: cacheDir).absolutePath
}

