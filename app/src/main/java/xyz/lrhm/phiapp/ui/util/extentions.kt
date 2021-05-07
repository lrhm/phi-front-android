package xyz.lrhm.phiapp.ui.util

import android.view.View
import xyz.lrhm.GetUserQuery
import xyz.lrhm.phiapp.databinding.ParametersContainerBinding
import kotlin.math.min

fun ParametersContainerBinding.bindTo(parameters: GetUserQuery.Parameters) {

    if (parameters.hold?.enabled == true) {
        hold.labelTextView.text = "زمان نگه داشتن:"

        val seconds = if (parameters.hold.value != 0) "${parameters.hold.value}"  +  " " + "ثانیه" else ""
        val minuts = if(parameters.hold.secondValue != 0) "${parameters.hold.secondValue}"  +  " " + "دقیقه" else ""

        var text = if (seconds != "") seconds else minuts

        if(minuts != "" && seconds != ""){
            text = minuts + " و " + seconds
        }

        hold.valueTextView.text = text


    } else {

        hold.root.visibility = View.GONE
    }


    if (parameters.repPerDay?.enabled == true) {
        repsPerDay.labelTextView.text = "تعداد تکرار در روز:"
        repsPerDay.valueTextView.text = "${parameters.repPerDay!!.value!!}"
    } else {

        repsPerDay.root.visibility = View.GONE
    }

    if (parameters.reps?.enabled == true) {
        reps.labelTextView.text = "تعداد تکرار"
        reps.valueTextView.text = "${parameters.reps!!.value!!}"
    } else {

        reps.root.visibility = View.GONE
    }

    if (parameters.sets?.enabled == true) {
        sets.labelTextView.text = "تعداد ست:"
        sets.valueTextView.text = "${parameters.sets!!.value!!}"
    } else {

        sets.root.visibility = View.GONE
    }

    if (parameters.restPerSet?.enabled == true) {
        rest.labelTextView.text = "زمان استراحت بین ست ها:"

        val seconds = if (parameters.restPerSet.value != 0) "${parameters.restPerSet.value}"  +  " " + "ثانیه" else ""
        val minuts = if(parameters.restPerSet.secondValue != 0) "${parameters.restPerSet.secondValue}"  +  " " + "دقیقه" else ""

        var text = if (seconds != "") seconds else minuts

        if(minuts != "" && seconds != ""){
            text = minuts + " و " + seconds
        }

        rest.valueTextView.text = text
    } else {

        rest.root.visibility = View.GONE
    }



    if (parameters.totalDuration?.enabled == true) {
        totalDuration.labelTextView.text = "مدت زمان تمرین؛"

        val seconds = if (parameters.totalDuration.value != 0) "${parameters.totalDuration.value}"  +  " " + "ثانیه" else ""
        val minuts = if(parameters.totalDuration.secondValue != 0) "${parameters.totalDuration.secondValue}"  +  " " + "دقیقه" else ""

        var text = if (seconds != "") seconds else minuts

        if(minuts != "" && seconds != ""){
            text = minuts + " و " + seconds
        }

        totalDuration.valueTextView.text = text
    } else {

        totalDuration.root.visibility = View.GONE
    }

}

fun ParametersContainerBinding.bindTo(parameters: GetUserQuery.Parameters1) {

    if (parameters.hold?.enabled == true) {
        hold.labelTextView.text = "زمان نگه داشتن:"

        val seconds = if (parameters.hold.value != 0) "${parameters.hold.value}"  +  " " + "ثانیه" else ""
        val minuts = if(parameters.hold.secondValue != 0) "${parameters.hold.secondValue}"  +  " " + "دقیقه" else ""

        var text = if (seconds != "") seconds else minuts

        if(minuts != "" && seconds != ""){
            text = minuts + " و " + seconds
        }

        hold.valueTextView.text = text


    } else {

        hold.root.visibility = View.GONE
    }


    if (parameters.repPerDay?.enabled == true) {
        repsPerDay.labelTextView.text = "تعداد تکرار در روز:"
        repsPerDay.valueTextView.text = "${parameters.repPerDay!!.value!!}"
    } else {

        repsPerDay.root.visibility = View.GONE
    }

    if (parameters.reps?.enabled == true) {
        reps.labelTextView.text = "تعداد تکرار"
        reps.valueTextView.text = "${parameters.reps!!.value!!}"
    } else {

        reps.root.visibility = View.GONE
    }

    if (parameters.sets?.enabled == true) {
        sets.labelTextView.text = "تعداد ست:"
        sets.valueTextView.text = "${parameters.sets!!.value!!}"
    } else {

        sets.root.visibility = View.GONE
    }

    if (parameters.restPerSet?.enabled == true) {
        rest.labelTextView.text = "زمان استراحت بین ست ها:"

        val seconds = if (parameters.restPerSet.value != 0) "${parameters.restPerSet.value}"  +  " " + "ثانیه" else ""
        val minuts = if(parameters.restPerSet.secondValue != 0) "${parameters.restPerSet.secondValue}"  +  " " + "دقیقه" else ""

        var text = if (seconds != "") seconds else minuts

        if(minuts != "" && seconds != ""){
            text = minuts + " و " + seconds
        }

        rest.valueTextView.text = text
    } else {

        rest.root.visibility = View.GONE
    }



    if (parameters.totalDuration?.enabled == true) {
        totalDuration.labelTextView.text = "مدت زمان تمرین؛"

        val seconds = if (parameters.totalDuration.value != 0) "${parameters.totalDuration.value}"  +  " " + "ثانیه" else ""
        val minuts = if(parameters.totalDuration.secondValue != 0) "${parameters.totalDuration.secondValue}"  +  " " + "دقیقه" else ""

        var text = if (seconds != "") seconds else minuts

        if(minuts != "" && seconds != ""){
            text = minuts + " و " + seconds
        }

        totalDuration.valueTextView.text = text
    } else {

        totalDuration.root.visibility = View.GONE
    }

}