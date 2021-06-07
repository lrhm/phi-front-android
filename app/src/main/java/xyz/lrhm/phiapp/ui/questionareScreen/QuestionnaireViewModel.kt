package xyz.lrhm.phiapp.ui.questionareScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.core.data.source.AppRepository
import javax.inject.Inject

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(val appRepository: AppRepository) : ViewModel() {


    fun getQuestionnairesForDayId(id: String): List<APIQuery.Questionare?> {


        return appRepository.getQuestionnairesForDay(id)!!
    }


}