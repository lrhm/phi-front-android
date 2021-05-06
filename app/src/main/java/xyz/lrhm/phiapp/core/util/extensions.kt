package xyz.lrhm.phiapp.core.util

import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import xyz.lrhm.GetUserQuery
import java.util.*

fun GetUserQuery.Day.getPersianDate(): PersianDate{

//    val pDate = PersianDate()
//    val format = PersianDateFormat()
  return  PersianDateFormat().parse(date.persianToEnglish(), "yyyy/MM/dd")
}

fun PersianDate.isSameDay(other: PersianDate): Boolean{
    return this.shDay == other.shDay && this.shMonth == other.shMonth && this.shYear == other.shYear
}


fun List<GetUserQuery.Day>?.getScheduleForDay(date: PersianDate): GetUserQuery.Day?{

    if(this != null)
    for (day in this){

        val pDate = day.getPersianDate()
        if(pDate.isSameDay(date))
            return day

    }

    return null
}

fun List<GetUserQuery.Day>.getTodaySchedule(): GetUserQuery.Day?{

    val today = PersianDate(Date())
    if(this != null)
    for (day in this){

        val pDate = day.getPersianDate()
        if(pDate.isSameDay(today))
            return day

    }

    return null
}

fun String.persianToEnglish():String {
    var result = ""
    var en = '0'
    for (ch in this) {
        en = ch
        when (ch) {
            '۰' -> en = '0'
            '۱' -> en = '1'
            '۲' -> en = '2'
            '۳' -> en = '3'
            '۴' -> en = '4'
            '۵' -> en = '5'
            '۶' -> en = '6'
            '۷' -> en = '7'
            '۸' -> en = '8'
            '۹' -> en = '9'
        }
        result = "${result}$en"
    }
    return result
}