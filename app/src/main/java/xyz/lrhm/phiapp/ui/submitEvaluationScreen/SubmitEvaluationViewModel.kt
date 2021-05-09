package xyz.lrhm.phiapp.ui.submitEvaluationScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.core.data.source.AppRepository
import xyz.lrhm.phiapp.ui.util.isDifficultyEnabled
import xyz.lrhm.phiapp.ui.util.isFatigueEnabled
import xyz.lrhm.phiapp.ui.util.isPainEnabled
import javax.inject.Inject

@HiltViewModel
class SubmitEvaluationViewModel @Inject constructor(val appRepository: AppRepository) :
    ViewModel() {
    // TODO: Implement the ViewModel

    val parameters = MutableLiveData<List<APIQuery.Parameter2>>()
    val exercise = MutableLiveData<APIQuery.Exercise>()

    val selectedPainValue = MutableLiveData(-1)
    val selectedDifficulty = MutableLiveData(-1)
    val selectedFatigueValue = MutableLiveData(-1)


    fun load(exerciseParameterId: String) {
        val parameter =
            appRepository.getParametersForDay(exerciseParameterId)!!
        val params = parameter.parameters!!.filter {
            it?.enabled == true && it?.name.contains("rest") == false
        }.map { it!! }

        exercise.value = appRepository.getExercise(parameter.exerciseId!!)

        parameters.value = params

    }

    fun isPainEnabled() = exercise.value!!.assesments.isPainEnabled()
    fun isDifficultyEnabled() = exercise.value!!.assesments.isDifficultyEnabled()
    fun isFatigueEnabled() = exercise.value!!.assesments.isFatigueEnabled()

    fun isComplete(): Boolean {
        if (isPainEnabled() && selectedPainValue.value == -1)
            return false
        if (isDifficultyEnabled() && selectedDifficulty.value == -1)
            return false
        if (isFatigueEnabled() && selectedFatigueValue.value == -1)
            return false


        return true
    }

    fun sendEvaluations(){

    }

}