package com.shsy.libbase.exts

import com.alibaba.android.arouter.launcher.ARouter
import com.shsy.libbase.base.AppManager
import com.shsy.libbase.base.BaseBean
import com.shsy.libbase.utils.DataStoreUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

/**
 * @ClassName BaseBean
 * @Description BaseBean扩展
 * @Author 嘟嘟侠
 * @Date 2021/11/1 11:07 上午
 * @Version 1.0
 */
suspend fun <T : BaseBean> BaseBean.doSuccess(successBlock: (suspend CoroutineScope.(T) -> Unit)? = null): BaseBean {
    return coroutineScope {
        if (code == 0) {
            @Suppress("UNCHECKED_CAST")
            successBlock?.invoke(this, this@doSuccess as T)
        }
        this@doSuccess
    }
}

suspend fun BaseBean.doError(errorBlock: (suspend CoroutineScope.(String) -> Unit)? = null): BaseBean {
    return coroutineScope {
        if (code != 0) {
            if (code == 401) {
                DataStoreUtil.putData(DataStoreUtil.USER_TOKEN, "")
                AppManager.finishAllActivity()
                ARouter.getInstance().build("/moduleUser/login").navigation()
            } else {
                errorBlock?.invoke(this, this@doError.msg)
            }
        }
        this@doError
    }
}