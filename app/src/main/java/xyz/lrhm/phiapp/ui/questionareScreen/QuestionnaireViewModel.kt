package xyz.lrhm.phiapp.ui.questionareScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.core.data.source.AppRepository
import javax.inject.Inject

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(val appRepository: AppRepository) : ViewModel() {


    val answers = mutableMapOf<String, APIQuery.Answer>()

    val questionnaires = MutableLiveData<List<APIQuery.Questionare?>>()

    fun getQuestionnaireForId(id: String): APIQuery.Questionare {
        return questionnaires.value?.find { it?.id == id }!!

    }

    fun clearAnswerForOption(questionId: String) {
        answers.remove(questionId)
    }

    fun isAnsweredForQuestion(questionId: String, optionId: String): Boolean {
        return answers[questionId]?.answeredOptionId == optionId
    }

    fun isQuestionAnswered(questionId: String): Boolean {
        return answers[questionId]?.answeredOptionId != null || answers[questionId]?.answerStr != null
    }

    fun setAnswerForOption(questionId: String, selectedOptionId: String) {
        answers[questionId] = APIQuery.Answer(
            id = "", questionId = questionId, answeredOptionId = selectedOptionId,
            answerStr = null
        )
    }

    fun setAnswerStrForQuestion(id: String, answer: String) {

        answers[id] = APIQuery.Answer(
            id = "", questionId = id, answeredOptionId = null,
            answerStr = answer
        )
    }

    fun initAnswerMapForQuestionnaire(id: String) {
        val questionnaire = getQuestionnaireForId(id)
        for (question in questionnaire.questions!!) {
            answers.put(
                question!!.id,
                APIQuery.Answer(
                    id = "", questionId = question!!.id, answeredOptionId = null,
                    answerStr = null
                )
            )
        }
    }

    fun loadForDay(dayId: String) {
        questionnaires.value = appRepository.getQuestionnairesForDay(dayId)!!
    }

    fun getQuestionnairesForDayId(id: String): List<APIQuery.Questionare?> {


        return appRepository.getQuestionnairesForDay(id)!!
    }


}