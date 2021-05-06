package xyz.lrhm.phiapp.ui.scheduleDayScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import saman.zamani.persiandate.PersianDate
import xyz.lrhm.GetUserQuery
import xyz.lrhm.phiapp.core.data.source.AppRepository
import javax.inject.Inject

@HiltViewModel
class ScheduleDayViewModel  @Inject constructor(val appRepository: AppRepository) : ViewModel() {

    val selectedDay = MutableLiveData<GetUserQuery.Day?>()

    val selectedDate= MutableLiveData<PersianDate>(PersianDate())

    fun getDayForSelectedDate(): GetUserQuery.Day?{

        return appRepository.getScheduleForDay(selectedDate.value!!)
    }

    fun setSelectedDate(date: PersianDate){

        selectedDate.value = date
        selectedDay.value = appRepository.getScheduleForDay(selectedDate.value!!)


    }

}