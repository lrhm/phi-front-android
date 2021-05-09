package xyz.lrhm.phiapp.ui.submitEvaluationScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.core.data.source.AppRepository
import javax.inject.Inject

@HiltViewModel
class SubmitEvaluationViewModel @Inject constructor(val appRepository: AppRepository) :
    ViewModel() {
    // TODO: Implement the ViewModel

    val parameters = MutableLiveData<List<APIQuery.Parameter2>>()
    val selectedPainValue = MutableLiveData(-1)

    fun getParams(exerciseParameterId: String) =
        appRepository.getParametersForDay(exerciseParameterId)!!.parameters!!.filter {
            it?.enabled == true && it?.name.contains("rest") == false
        }.map { it!! }

    fun load(exerciseParameterId: String) {
        parameters.value = getParams(exerciseParameterId)
    }
}