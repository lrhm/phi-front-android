package xyz.lrhm.phiapp.ui.questionareScreen

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.MobileNavigationDirections
import xyz.lrhm.phiapp.databinding.QuestionnaireSelectItemBinding
import xyz.lrhm.phiapp.databinding.ScheduleExerciseItemBinding
import xyz.lrhm.phiapp.ui.util.bindTo
import xyz.lrhm.phiapp.ui.util.isAfterToday
import xyz.lrhm.phiapp.ui.util.isExerciseDone
import xyz.lrhm.phiapp.ui.util.isToday


class QuestionnaireSelectRecyclerViewAdapter(
    private val values: List<APIQuery.Questionare?>,
    private val parent: Fragment,

) : RecyclerView.Adapter<QuestionnaireSelectRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val holder = ViewHolder(
            QuestionnaireSelectItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

//        val width = parent.context.resources.displayMetrics.widthPixels * 0.3
//        holder.binding.imageView.layoutParams.width = width.toInt()
//        holder.binding.imageView.layoutParams.height = width.toInt()

        return holder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]!!

        holder.binding.titleTextView.text = item.title
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: QuestionnaireSelectItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}