package com.shsy.libbase

import com.shsy.libbase.exts.logV
import com.shsy.libbase.utils.DateUtil
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//
//        var a: List<String>? = null
//        a = "".split(",")
//
//        println(a.isNullOrEmpty())

//        println(getWeekMonday())


        print(DateUtil.nowTimeInSection("2021-12-22 10:00", "2021-12-22 10:30"))

    }

    fun getWeekMonday(): String? {
        val formater = SimpleDateFormat("MM/dd/yyyy")
        val cal: Calendar = GregorianCalendar()
        cal.firstDayOfWeek = Calendar.MONDAY
        cal.time = Date()
        cal[Calendar.DAY_OF_WEEK] = cal.firstDayOfWeek
        val first = cal.time

        return formater.format(first)
    }
}