package com.shsy.libbase.utils

import android.annotation.SuppressLint
import com.shsy.libbase.exts.logV
import java.text.SimpleDateFormat
import java.util.*

/**
 * @ClassName DateUtil
 * @Description 时间和日期工具类。
 * @Author 嘟嘟侠
 * @Date 2021/10/29 10:15 上午
 * @Version 1.0
 */
object DateUtil {
    private const val MINUTE = (60 * 1000).toLong()

    private const val HOUR = 60 * MINUTE

    private const val DAY = 24 * HOUR

    private const val WEEK = 7 * DAY

    private const val MONTH = 4 * WEEK

    private const val YEAR = 365 * DAY

    /**
     * 根据传入的Unix时间戳，获取转换过后更加易读的时间格式。
     * @param dateMillis
     * Unix时间戳
     * @return 转换过后的时间格式，如2分钟前，1小时前。
     */
    fun getConvertedDate(dateMillis: Long): String {
        val currentMillis = System.currentTimeMillis()
        val timePast = currentMillis - dateMillis
        if (timePast > -MINUTE) { // 采用误差一分钟以内的算法，防止客户端和服务器时间不同步导致的显示问题
            when {
                /*timePast < HOUR -> {
                    var pastMinutes = timePast / MINUTE
                    if (pastMinutes <= 0) {
                        pastMinutes = 1
                    }
                    return pastMinutes.toString() + GlobalUtil.getString(R.string.minutes_ago)
                }*/
                timePast < DAY -> {
                    var pastHours = timePast / HOUR
                    if (pastHours <= 0) {
                        pastHours = 1
                    }
                    /*return pastHours.toString() + GlobalUtil.getString(R.string.hours_ago)*/
                    return getDateAndHourMinuteTime(dateMillis)
                }
                timePast < WEEK -> {
                    var pastDays = timePast / DAY
                    if (pastDays <= 0) {
                        pastDays = 1
                    }
                    return "$pastDays 天前}"
                }
                timePast < MONTH -> {
                    var pastDays = timePast / WEEK
                    if (pastDays <= 0) {
                        pastDays = 1
                    }
                    return "$pastDays 周前"
                }
                else -> return getDate(dateMillis)
            }
        } else {
            return getDateAndTime(dateMillis)
        }
    }

    fun isBlockedForever(timeLeft: Long) = timeLeft > 5 * YEAR

    fun getDate(dateMillis: Long, pattern: String = "yyyy-MM-dd"): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(Date(dateMillis))
    }

    fun getTimerFormatString(dateSecond: Int): String {
        var dateSecondTemp = dateSecond

        val hour = dateSecondTemp / 3600
        dateSecondTemp -= hour * 3600
        val minute = dateSecondTemp / 60
        val second = dateSecondTemp % 60

        val hourStr = if (hour <= 9) "0$hour" else hour
        val minuteStr = if (minute <= 9) "0$minute" else minute
        val secondStr = if (second <= 9) "0$second" else second

        return "$hourStr:$minuteStr:$secondStr"
    }

    private fun getDateAndTime(dateMillis: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(Date(dateMillis))
    }

    private fun getDateAndHourMinuteTime(dateMillis: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(dateMillis))
    }

    /**
     * 获取本周周一日期
     */
    fun getWeekStart(): String {
        val cal = Calendar.getInstance()
        cal.firstDayOfWeek = Calendar.MONDAY
        cal.time = Date()
        cal[Calendar.DAY_OF_WEEK] = cal.firstDayOfWeek // Monday

        val time = cal.time
        return SimpleDateFormat("yyyy-MM-dd").format(time)
    }

    /**
     * 获取本周最后一天
     */
    fun getWeekEnd(): String {
        val cal = Calendar.getInstance()
        cal.firstDayOfWeek = Calendar.MONDAY
        cal.time = Date()
        cal[Calendar.DAY_OF_WEEK] = cal.firstDayOfWeek + 6 // Sunday

        val time = cal.time
        return SimpleDateFormat("yyyy-MM-dd").format(time)
    }

    /**
     * 时间是否在区间里
     * @param start 2020-12-12 12:20
     * @param end 2020-12-12 12:25
     * @return 0 小于区间 1 在区间中 2 大于区间
     */
    @SuppressLint("SimpleDateFormat")
    fun nowTimeInSection(start: String, end: String): Int {
        val startTime = SimpleDateFormat("yyyy-MM-dd HH:mm").parse(start).time
        val endTime = SimpleDateFormat("yyyy-MM-dd HH:mm").parse(end).time
        val nowTime = System.currentTimeMillis()
        return when {
            nowTime < startTime -> 0
            nowTime > startTime && nowTime < endTime -> 1
            nowTime > endTime -> 2
            else -> -1
        }
    }
}