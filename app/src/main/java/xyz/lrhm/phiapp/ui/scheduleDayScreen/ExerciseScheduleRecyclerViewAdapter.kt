package xyz.lrhm.phiapp.ui.scheduleDayScreen

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import xyz.lrhm.GetUserQuery
import xyz.lrhm.phiapp.databinding.ExerciseItemBinding
import xyz.lrhm.phiapp.databinding.ScheduleExerciseItemBinding


class ExerciseScheduleRecyclerViewAdapter(
    private val values: List<GetUserQuery.Parameter?>,
    private val exercises: List<GetUserQuery.Exercise?>

) : RecyclerView.Adapter<ExerciseScheduleRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ScheduleExerciseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]!!

        holder.binding.titleTextView.text = item.title
//
        val exercise = exercises.find { it!!.id == item.exerciseId }
        val url = exercise!!.pictures[0]!!.url.replace("localhost", "192.168.2.5")
        Glide.with(holder.binding.imageView).load(url).into(holder.binding.imageView)

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: ScheduleExerciseItemBinding) : RecyclerView.ViewHolder(binding.root)



}