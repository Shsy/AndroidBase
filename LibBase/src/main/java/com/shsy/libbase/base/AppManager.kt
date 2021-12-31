package com.shsy.libbase.base

import android.app.Activity
import java.util.*

/**
 * @ClassName AppManager
 * @Description activity堆栈式管理
 * @Author 嘟嘟侠
 * @Date 2021/10/28 3:10 下午
 * @Version 1.0
 */
object AppManager {

    private val activityStack: Stack<Activity> = Stack()

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    /**
     * 移除指定的Activity
     */
    fun removeActivity(activity: Activity) {
        activityStack.remove(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity? = null) {
        val needFinishActivity = activity ?: activityStack.lastElement()
        if (!needFinishActivity.isFinishing) {
            needFinishActivity.finish()
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(clazz: Class<*>) {
        val needFinishActivity = activityStack.find { it::class.java == clazz }
        needFinishActivity?.finish()
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        activityStack.forEach { activity ->
            activity.finish()
        }
        activityStack.clear()
    }
}