package xyz.lrhm.phiapp.ui.questionareScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.core.data.source.AppRepository
import javax.inject.Inject

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(val appRepository: AppRepository) : ViewModel() {


    val questionnaires = MutableLiveData<List<APIQuery.Questionare?>>()

    fun getQuestionnaireForId(id: String): APIQuery.Questionare {
        return questionnaires.value?.find { it?.id == id }!!

    }

    fun loadForDay(dayId: String) {
        questionnaires.value = appRepository.getQuestionnairesForDay(dayId)!!
    }

    fun getQuestionnairesForDayId(id: String): List<APIQuery.Questionare?> {


        return appRepository.getQuestionnairesForDay(id)!!
    }


}