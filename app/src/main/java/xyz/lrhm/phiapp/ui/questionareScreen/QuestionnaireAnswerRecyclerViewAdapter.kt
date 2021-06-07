package xyz.lrhm.phiapp.ui.questionareScreen

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import timber.log.Timber
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.MobileNavigationDirections
import xyz.lrhm.phiapp.databinding.AnswerSelectorItemBinding
import xyz.lrhm.phiapp.databinding.QuestionnaireSelectItemBinding
import xyz.lrhm.phiapp.databinding.ScheduleExerciseItemBinding
import xyz.lrhm.phiapp.ui.util.bindTo
import xyz.lrhm.phiapp.ui.util.isAfterToday
import xyz.lrhm.phiapp.ui.util.isExerciseDone
import xyz.lrhm.phiapp.ui.util.isToday


class QuestionnaireAnswerRecyclerViewAdapter(
    val question: APIQuery.Question,
    val viewModel: QuestionnaireViewModel,
    private val values: List<APIQuery.Option?>,
    private val parent: Fragment,

    ) : RecyclerView.Adapter<QuestionnaireAnswerRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val holder = ViewHolder(
            AnswerSelectorItemBinding.inflate(
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

        holder.binding.checkBox.setOnCheckedChangeListener(null)
        holder.binding.checkBox.isChecked = viewModel.isAnsweredForQuestion(question.id, item.id)

        holder.binding.checkBox.text = item.value
        holder.binding.checkBox.setOnCheckedChangeListener { compoundButton, checked ->

            if(!compoundButton.isPressed) {
                return@setOnCheckedChangeListener;
            }
            if (checked && !viewModel.isAnsweredForQuestion(question.id, item.id)) {
                viewModel.setAnswerForOption(
                    question.id,
                    item.id
                )
                notifyDataSetChanged()
            } else {
                viewModel.clearAnswerForOption(
                    question.id
                )

            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: AnswerSelectorItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}