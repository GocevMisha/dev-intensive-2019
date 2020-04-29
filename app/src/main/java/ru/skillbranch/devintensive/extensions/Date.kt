package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern : String = "HH:mm:ss dd.MM.yy") : String{
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}
fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND) : Date{
    var time = this.time
    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}
fun Date.humanizeDiff(date: Date = Date()): String{

    return when(val  period = date.time - this.time){
        in 0..1* SECOND -> "только что"
        in 1*SECOND..45*SECOND -> "несколько секунд назад"
        in 45*SECOND..75*SECOND -> "минуту назад"
        in 75* SECOND..45* MINUTE -> "${period/ MINUTE} минут назад"
        in 45* MINUTE..75* MINUTE ->  "час назад"
        in 75* MINUTE..22* HOUR -> "${period/ HOUR} часов назад"
        in 22* HOUR..26* HOUR -> "день назад"
        in 26* HOUR..360* DAY -> "${period/ DAY} дней назад"
        else -> "более года назад"
    }
}
enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}