package com.example.daysrecord

import com.example.daysrecord.logic.model.Message
import com.example.daysrecord.logic.model.Result
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class Utils {

    companion object {

        val week = listOf("周一", "周二", "周三", "周四", "周五", "周六", "周日")

        @JvmStatic
        fun getTransformedTime(time: String): String {
            val res = time.split('-',' ')
            val now = LocalDate.now()
            return if (now.year != res[0].toInt()) {
                // 记录的日期与现在不在同一年
                // 使用的格式为 yyyy年mm月dd日 HH:MM
                "${res[0]}年${res[1]}月${res[2]}日 ${res[3]}"
            } else if (now.monthValue == res[1].toInt() && now.dayOfMonth - now.dayOfWeek.value < res[2].toInt()) {
                // 记录的日期与现在是同一周
                // 使用的格式为 周x HH:MM
                // 计算time对应的是周几
                val dayOfWeek = res[2].toInt() - (now.dayOfMonth - now.dayOfWeek.value)
                "${week[dayOfWeek - 1]} ${res[3]}"
            } else {
                // 记录的日期与现在在同一年但不在同一周
                // 使用的格式为 mm月dd日 HH:MM
                "${res[1]}月${res[2]}日 ${res[3]}"
            }
        }

        fun getDefaultTime(): String {
            val date = LocalDate.now()
            val time = "${LocalTime.now().hour}:${LocalTime.now().minute}"
            return "$date $time"
        }

        fun <T> Result<T>.dataConvert(): List<T>? {
            if (code == 200) {
                return data
            } else {
                return null
            }
        }

    }

}