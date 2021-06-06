package xyz.lrhm.phiapp.core.util

import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import timber.log.Timber
import xyz.lrhm.APIQuery
import java.util.*

fun APIQuery.Day.getPersianDate(): PersianDate {

//    val pDate = PersianDate()
//    val format = PersianDateFormat()
    return PersianDateFormat().parse(date.persianToEnglish(), "yyyy/MM/dd")
}

fun PersianDate.isSameDay(other: PersianDate): Boolean {
    return this.shDay == other.shDay && this.shMonth == other.shMonth && this.shYear == other.shYear
}


fun List<APIQuery.Day>?.getScheduleForDay(date: PersianDate): APIQuery.Day? {

    if (this != null)
        for (day in this) {

            val pDate = day.getPersianDate()
            if (pDate.isSameDay(date))
                return day

        }

    return null
}

fun List<APIQuery.Day>.getTodaySchedule(): APIQuery.Day? {

    val today = PersianDate(Date())
    if (this != null)
        for (day in this) {

            val pDate = day.getPersianDate()
            if (pDate.isSameDay(today))
                return day

        }

    return null
}


fun TextView.setPersianTextHelper() {

    this.doOnTextChanged { text, start, before, count ->


        Timber.d("textChanded, ${text}")
        val prsnFromt = text.toString().englishToPersian()
        if (text.toString() != prsnFromt)
            setText(prsnFromt)
    }
}

fun String.englishToPersian(): String {
    var result = ""
    var en = '0'
    for (ch in this) {
        en = ch
        when (ch) {
            '0' -> en = '۰'
            '1' -> en = '۱'
            '2' -> en = '۲'
            '3' -> en = '۳'
            '4' -> en = '۴'
            '5' -> en = '۵'
            '6' -> en = '۶'
            '7' -> en = '۷'
            '8' -> en = '۸'
            '9' -> en = '۹'
        }
        result = "${result}$en"
    }
    return result
}

fun String.persianToEnglish(): String {
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


