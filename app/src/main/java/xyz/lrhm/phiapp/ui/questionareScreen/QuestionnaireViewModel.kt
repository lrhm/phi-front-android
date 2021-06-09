package xyz.lrhm.phiapp.ui.questionareScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.lrhm.APIQuery
import xyz.lrhm.SubmitQuestionareMutation
import xyz.lrhm.phiapp.core.data.model.ResultOf
import xyz.lrhm.phiapp.core.data.source.AppRepository
import xyz.lrhm.phiapp.ui.util.toInput
import xyz.lrhm.type.EvaluationInput
import xyz.lrhm.type.QuestionAnswerInput
import xyz.lrhm.type.QuestionareAnswerInput
import javax.inject.Inject

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(val appRepository: AppRepository) : ViewModel() {


    val answers = mutableMapOf<String, APIQuery.Answer>()

    val selectedQuestionnaire = MutableLiveData<APIQuery.Questionare>()

    val questionnaires = MutableLiveData<List<APIQuery.Questionare?>>()

    val result = MutableLiveData<ResultOf<SubmitQuestionareMutation.SubmitQuestionare>>()

    fun getQuestionnaireForId(id: String): APIQuery.Questionare {
        return questionnaires.value?.find { it?.id == id }!!

    }

    fun clearAnswerForOption(questionId: String, optionId: String) {
        if (answers[questionId]?.answeredOptionId == optionId)
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
            id = "",
            questionId = id, answeredOptionId = null,
            answerStr = answer
        )
    }

    fun initAnswerMapForQuestionnaire(id: String) {
        val questionnaire = getQuestionnaireForId(id)
        selectedQuestionnaire.postValue((questionnaire))
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

    fun isAnswered(answer: APIQuery.Answer): Boolean {

        if(answer.answerStr != null && answer.answerStr != "")
            return true
        if(answer.answeredOptionId != null)
            return true
        return false

    }

    fun isAllAnswered(): Boolean {

        for (answer in answers.values) {
            if(isAnswered(answer).not())
                return false

        }

        return true
    }

    fun sendQuestionnaireAnswer(
        dayId: String, questionareId: String
    ) {
        if (isAllAnswered().not()) {
            return
        }
        val answers: List<QuestionAnswerInput> = this.answers.values.map {
            QuestionAnswerInput(
                it.questionId,
                it.answeredOptionId.toInput(),
                answerStr = it.answerStr.toInput()
            )
        }

        val input = QuestionareAnswerInput(
            questionareId,
            dayId,
            answers.toInput()

        )

        viewModelScope.launch {
            val res = appRepository.remoteDataSource.submitQuestionnaire(input)

            result.postValue(res)

            Timber.d("API SUBMIT RES IZ ${res}")
        }
    }

}