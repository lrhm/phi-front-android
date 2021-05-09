package xyz.lrhm.phiapp.core.data.source

import saman.zamani.persiandate.PersianDate
import xyz.lrhm.APIQuery
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

//    var user: APIQuery.User? = null

    fun isLoggedIn() = cacheUtil.getToken() != ""

    fun getCachedAPIRes() = remoteDataSource.cachedUser!!

    val user = remoteDataSource.user

    fun getTodaySchedule(): APIQuery.Day? {

        val today = PersianDate(Date())

        return getScheduleForDay(today)
    }

    fun getExercises() = getCachedAPIRes().patient!!.schedule!!.exercises!!

    fun getExercise(id: String) = getExercises().find { it?.id == id }!!

//    fun

//    fun getEvaluationsF

    fun getParametersForDay(exerciseParameterId: String): APIQuery.Parameter2? {

        val days = getCachedAPIRes().patient!!.schedule!!.days!!
        for(day in days){

            for(param in day?.parameters!!){
                if(param?.id == exerciseParameterId)
                    return param
            }
        }

        return null

    }

    fun getScheduleForDay(date: PersianDate): APIQuery.Day?{
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