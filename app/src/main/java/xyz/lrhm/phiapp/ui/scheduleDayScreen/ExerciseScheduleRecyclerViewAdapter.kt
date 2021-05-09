package xyz.lrhm.phiapp.ui.scheduleDayScreen

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.MobileNavigationDirections
import xyz.lrhm.phiapp.databinding.ScheduleExerciseItemBinding
import xyz.lrhm.phiapp.ui.util.bindTo
import xyz.lrhm.phiapp.ui.util.isAfterToday
import xyz.lrhm.phiapp.ui.util.isExerciseDone
import xyz.lrhm.phiapp.ui.util.isToday


class ExerciseScheduleRecyclerViewAdapter(
    private val values: List<APIQuery.Parameter2?>,
    private val exercises: List<APIQuery.Exercise?>,
    private val parent: Fragment,
    private val day: APIQuery.Day

) : RecyclerView.Adapter<ExerciseScheduleRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val holder = ViewHolder(
            ScheduleExerciseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        val width = parent.context.resources.displayMetrics.widthPixels * 0.3
        holder.binding.imageView.layoutParams.width = width.toInt()
        holder.binding.imageView.layoutParams.height = width.toInt()

        return holder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]!!
        val exercise = exercises.find { it!!.id == item.exerciseId }!!

        holder.binding.titleTextView.text = exercise.title

        holder.binding.parametersContainer.bindTo(
            item.parameters!!
        )
//
        val url = exercise.pictures[0]!!.url.replace("localhost", "192.168.2.5")
        Glide.with(holder.binding.imageView).load(url).into(holder.binding.imageView)


        holder.binding.openButton.setOnClickListener {
            val direction = MobileNavigationDirections.actionGlobalExerciseFragment(
                exercise.id, item.id, day.id

            )
            parent.findNavController().navigate(direction)
        }

        if (day.isExerciseDone(item.exerciseId!!)) {
            holder.binding.submitEvaluationButton.text = "انجام شد"
            holder.binding.submitEvaluationButton.setOnClickListener {

            }

        } else {

            holder.binding.submitEvaluationButton.isEnabled = !day.isAfterToday()

            holder.binding.submitEvaluationButton.text = "ثبت عملکرد"
            holder.binding.submitEvaluationButton.setOnClickListener {
                val direction =
                    MobileNavigationDirections.actionGlobalSubmitEvaluationFragment(item.id, day.id)
                parent.findNavController().navigate(direction)

            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: ScheduleExerciseItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}