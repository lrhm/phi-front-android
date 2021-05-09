package xyz.lrhm.phiapp.ui.scheduleDayScreen

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.MobileNavigationDirections
import xyz.lrhm.phiapp.databinding.ScheduleExerciseItemBinding
import xyz.lrhm.phiapp.ui.util.bindTo


class ExerciseScheduleRecyclerViewAdapter(
    private val values: List<APIQuery.Parameter2?>,
    private val exercises: List<APIQuery.Exercise?>,
    private val parent: Fragment,
    private val dayId: String

    ) : RecyclerView.Adapter<ExerciseScheduleRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val holder = ViewHolder(
            ScheduleExerciseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        val width =  parent.context.resources.displayMetrics.widthPixels * 0.3
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
          val direction=  MobileNavigationDirections.actionGlobalExerciseFragment(exercise.id, item.id, dayId)
            parent.findNavController().navigate(direction)
        }

        holder.binding.submitEvaluationButton.setOnClickListener {
            val direction = MobileNavigationDirections.actionGlobalSubmitEvaluationFragment(item.id, dayId)
            parent.findNavController().navigate(direction)

        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: ScheduleExerciseItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}