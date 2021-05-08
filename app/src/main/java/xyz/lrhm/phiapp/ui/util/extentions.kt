package xyz.lrhm.phiapp.ui.util

import android.view.View
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.databinding.DottedLabelTextBinding
import xyz.lrhm.phiapp.databinding.ParametersContainerBinding
import xyz.lrhm.type.ParameterType
import kotlin.math.min


fun ParametersContainerBinding.bindTo(parameters: List<APIQuery.Parameter2?>) {

    val viewLists = listOf( repsPerDay, reps, hold,  sets, rest,  totalDuration)

    for (i in 0 until viewLists.size){
        val param = parameters[i]!!
        val v = viewLists[i]

        if(param.enabled) {
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
        } else{
            v.root.visibility = View.GONE
        }

    }

}


