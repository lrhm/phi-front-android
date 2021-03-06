package xyz.lrhm.phiapp.ui.scheduleDayScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import saman.zamani.persiandate.PersianDate
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.core.data.source.AppRepository
import javax.inject.Inject

@HiltViewModel
class ScheduleDayViewModel  @Inject constructor(val appRepository: AppRepository) : ViewModel() {

    val selectedDay = MutableLiveData<APIQuery.Day?>()

    val selectedDate= MutableLiveData<PersianDate>(PersianDate())

    fun getExercises() = appRepository.getCachedAPIRes().patient!!.schedule!!.exercises!!

    fun getDayForSelectedDate(): APIQuery.Day?{

        return appRepository.getScheduleForDay(selectedDate.value!!)
    }

    fun getQuestionnairesForDayId(id: String = selectedDay.value!!.id): List<APIQuery.Questionare?> {


        return appRepository.getQuestionnairesForDay(id)!!
    }



    fun setSelectedDate(date: PersianDate){

        selectedDate.value = date
        selectedDay.value = appRepository.getScheduleForDay(selectedDate.value!!)


    }

    init {
        setSelectedDate(PersianDate())


    }
}