package com.shsy.libbase.base

/**
 * @ClassName BaseApplication
 * @Description BaseApplication
 * @Author 嘟嘟侠
 * @Date 2021/10/28 3:08 下午
 * @Version 1.0
 */
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.shsy.libbase.BuildConfig

abstract class BaseApplication : Application() {

    /**
     * 全局Context
     */
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var CONTEXT: Context
        private lateinit var serverBaseUrl: String
        fun getContext() = CONTEXT
        fun getServerBaseUrl() = serverBaseUrl
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = this@BaseApplication
        serverBaseUrl = serverBaseUrl()

        // AppManager 用到
        initActivityLifecycleCallbacks()

        // 页面路由
        initARouter()
    }

    protected abstract fun serverBaseUrl(): String

    private fun initActivityLifecycleCallbacks() {
        this.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                AppManager.addActivity(p0)
            }

            override fun onActivityStarted(p0: Activity) {

            }

            override fun onActivityResumed(p0: Activity) {

            }

            override fun onActivityPaused(p0: Activity) {

            }

            override fun onActivityStopped(p0: Activity) {

            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

            }

            override fun onActivityDestroyed(p0: Activity) {
                AppManager.removeActivity(p0)
            }
        })
    }

    // ARouter初始化
    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }

        ARouter.init(this@BaseApplication)
    }
}