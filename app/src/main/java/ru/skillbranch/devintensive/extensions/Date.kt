package ru.skillbranch.devintensive.extensions

import android.util.Log
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
    println(date.time - this.time)
    return when(val  period = date.time - this.time){
        in -360* DAY.. -26* HOUR -> "через ${TimeUnits.DAY.plural(((-period/ DAY)+1).toInt())}"
        in -22* HOUR downTo -26* HOUR -> "через день"
        in -75* MINUTE downTo -22* HOUR -> "через ${TimeUnits.HOUR.plural(((-period/ HOUR)+1).toInt())}"
        in -45* MINUTE downTo -75* MINUTE ->  "через час"
        in -75* SECOND downTo -45* MINUTE -> "через ${TimeUnits.MINUTE.plural(((-period/ MINUTE)+1).toInt())}"
        in -45*SECOND downTo -75*SECOND -> "через минуту"
        in -1*SECOND downTo -45*SECOND -> "через несколько секунд"
        in -1* SECOND..1* SECOND -> "только что"
        in 1*SECOND..45*SECOND -> "несколько секунд назад"
        in 45*SECOND..75*SECOND -> "минуту назад"
        in 75* SECOND..45* MINUTE -> "${TimeUnits.MINUTE.plural((period/ MINUTE).toInt())} назад"
        in 45* MINUTE..75* MINUTE ->  "час назад"
        in 75* MINUTE..22* HOUR -> "${TimeUnits.HOUR.plural((period/ HOUR).toInt())} назад"
        in 22* HOUR..26* HOUR -> "день назад"
        in 26* HOUR..360* DAY -> "${TimeUnits.DAY.plural((period/ DAY).toInt())} назад"
        else -> if(period<-360* HOUR) "более чем через год"
            else "более года назад"
    }
}
enum class TimeUnits{
    SECOND {
        override fun plural(value: Int): String {
            return when(value){
                in 11..14 -> "$value секунд"
                else -> when(value%10){
                    1 -> "$value секунду"
                    in 2..4 -> "$value секунды"
                    else ->"$value секунд"
                }
            }
        }
    },
    MINUTE {
        override fun plural(value: Int): String {
            return when(value){
                in 11..14 -> "$value минут"
                else -> when(value%10){
                    1 -> "$value минуту"
                    in 2..4 -> "$value минуты"
                    else ->"$value минут"
                }
            }
        }
    },
    HOUR {
        override fun plural(value: Int): String {
            return when(value){
                in 11..14 -> "$value часов"
                else -> when(value%10){
                    1 -> "$value час"
                    in 2..4 -> "$value часа"
                    else ->"$value часов"
                }
            }
        }
    },
    DAY {
        override fun plural(value: Int): String {
            return when(value){
                in 11..14 -> "$value дней"
                else -> when(value%10){
                    1 -> "$value день"
                    in 2..4 -> "$value дня"
                    else ->"$value дней"
                }
            }
        }
    };

    abstract fun plural(value: Int): String
}