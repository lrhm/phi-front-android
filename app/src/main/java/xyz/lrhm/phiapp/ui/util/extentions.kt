package xyz.lrhm.phiapp.ui.util

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.databinding.DottedLabelTextBinding
import xyz.lrhm.phiapp.databinding.ParameterEvaluationItemBinding
import xyz.lrhm.phiapp.databinding.ParameterEvaluationListBinding
import xyz.lrhm.phiapp.databinding.ParametersContainerBinding
import xyz.lrhm.type.ParameterType
import kotlin.math.min


fun ParametersContainerBinding.bindTo(parameters: List<APIQuery.Parameter2?>) {

    val viewLists = listOf(repsPerDay, reps, hold, sets, rest, totalDuration)

    for (i in 0 until viewLists.size) {
        val param = parameters[i]!!
        val v = viewLists[i]

        if (param.enabled) {
            v.labelTextView.text = param.title + ":"

            if (param.valueType == ParameterType.TIME) {
                val seconds = if (param.value != 0) "${param.value}" + " " + "ثانیه" else ""
                val minuts =
                    if (param.secondValue != 0) "${param.secondValue}" + " " + "دقیقه" else ""

                var text = if (seconds != "") seconds else minuts

                if (minuts != "" && seconds != "") {
                    text = minuts + " و " + seconds
                }
                v.valueTextView.text = text
            } else {
                v.valueTextView.text = "${param.value}"
            }
        } else {
            v.root.visibility = View.GONE
        }

    }
}

fun ParametersContainerBinding.bindToA(parameters: List<APIQuery.Parameter?>) {

    val viewLists = listOf(repsPerDay, reps, hold, sets, rest, totalDuration)

    for (i in 0 until viewLists.size) {
        val param = parameters[i]!!
        val v = viewLists[i]

        if (param.enabled) {
            v.labelTextView.text = param.title + ":"

            if (param.valueType == ParameterType.TIME) {
                val seconds = if (param.value != 0) "${param.value}" + " " + "ثانیه" else ""
                val minuts =
                    if (param.secondValue != 0) "${param.secondValue}" + " " + "دقیقه" else ""

                var text = if (seconds != "") seconds else minuts

                if (minuts != "" && seconds != "") {
                    text = minuts + " و " + seconds
                }
                v.valueTextView.text = text
            } else {
                v.valueTextView.text = "${param.value}"
            }
        } else {
            v.root.visibility = View.GONE
        }
    }


}

fun ParameterEvaluationListBinding.bindTo(
    evaluations: List<APIQuery.Parameter2>,
    liveData: MutableLiveData<List<APIQuery.Parameter2>>
) {


//    Timber.d("binding to  parameters in evaluation $evaluations")

    val viewLists = mutableListOf(repsPerDay, reps, hold, sets, totalDuration)

    for (i in 0 until viewLists.size) {
        val v = viewLists[i]
        if (i >= evaluations.size) {
            v.root.visibility = View.GONE
            continue
        }
        val param = evaluations[i]!!


        if (param.enabled) {

            v.minusButton.setOnClickListener {

                if (param.value!! > 0) {
                    val newParam = param.copy(value = param.value?.minus(1))
                    val newList = evaluations.toMutableList()
                    newList[i] = newParam
                    liveData.value = newList

                } else if (param.secondValue != 0) {
                    val newParam = param.copy(secondValue = param.secondValue?.minus(1))
                    val newList = evaluations.toMutableList()
                    newList[i] = newParam
                    liveData.value = newList
                }


            }

            v.plusButton.setOnClickListener {
                if (param.value != null) {


                    val newParam = param.copy(value = param.value?.plus(1))
                    val newList = evaluations.toMutableList()
                    newList[i] = newParam
                    liveData.value = newList
                    return@setOnClickListener

                }
                if (param.secondValue != 0) {
                    val newParam = param.copy(secondValue = param.secondValue?.plus(1))
                    val newList = evaluations.toMutableList()
                    newList[i] = newParam
                    liveData.value = newList
                }

            }

            v.titleTextView.text = param.title + ":"
            v.valueTextView.text = "${param.value}"



            if (param.valueType == ParameterType.TIME) {
                val seconds = if (param.value != 0) "${param.value}" + " " + "ثانیه" else ""
                val minuts =
                    if (param.secondValue != 0) "${param.secondValue}" + " " + "دقیقه" else ""

                var text = if (seconds != "") seconds else minuts

                if (minuts != "" && seconds != "") {
                    text = minuts + " و " + seconds
                }
                if(text == "")
                    text = "0 ثانیه"

                v.valueTextView.text = text
            } else {
                v.valueTextView.text = "${param.value}"
            }
        } else {
            v.root.visibility = View.GONE
        }
    }


}

fun Context.getWidth() = resources.displayMetrics.widthPixels
fun Context.getHeight() = resources.displayMetrics.heightPixels
