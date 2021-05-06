package xyz.lrhm.phiapp.core.data.source

import saman.zamani.persiandate.PersianDate
import xyz.lrhm.GetUserQuery
import xyz.lrhm.phiapp.core.data.source.remoteDataSource.RemoteDataSource
import xyz.lrhm.phiapp.core.util.*
import java.util.*

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    val cacheUtil: CacheUtil
) {

    suspend fun doLogin(username: String, password: String) = remoteDataSource.login(username, password)


    fun isLoggedIn() = cacheUtil.getToken() != ""

    fun getCachedAPIRes() = cacheUtil.user.value!!

    fun getTodaySchedule(): GetUserQuery.Day? {

        val today = PersianDate(Date())

        return getScheduleForDay(today)
    }

    fun getScheduleForDay(date: PersianDate): GetUserQuery.Day?{
        val user = getCachedAPIRes()

        user.patient?.schedule?.days.apply {
            if(this != null)
                for (day in this){

                    val pDate = day!!.getPersianDate()
                    if(pDate.isSameDay(date))
                        return day

                }
        }

        return null
    }
}