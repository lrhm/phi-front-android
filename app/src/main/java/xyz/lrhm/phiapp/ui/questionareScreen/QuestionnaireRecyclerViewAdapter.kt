package xyz.lrhm.phiapp.ui.questionareScreen

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.MobileNavigationDirections
import xyz.lrhm.phiapp.databinding.QuestionAnswerItemBinding
import xyz.lrhm.phiapp.databinding.QuestionnaireSelectItemBinding
import xyz.lrhm.phiapp.databinding.ScheduleExerciseItemBinding
import xyz.lrhm.phiapp.ui.util.bindTo
import xyz.lrhm.phiapp.ui.util.isAfterToday
import xyz.lrhm.phiapp.ui.util.isExerciseDone
import xyz.lrhm.phiapp.ui.util.isToday
import xyz.lrhm.type.QuestionAnswerType


class QuestionnaireRecyclerViewAdapter(
    val viewModel: QuestionnaireViewModel,
    private val values: List<APIQuery.Question?>,
    private val parent: Fragment,

    ) : RecyclerView.Adapter<QuestionnaireRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val holder = ViewHolder(
            QuestionAnswerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        holder.setIsRecyclable(false)

//        val width = parent.context.resources.displayMetrics.widthPixels * 0.3
//        holder.binding.imageView.layoutParams.width = width.toInt()
//        holder.binding.imageView.layoutParams.height = width.toInt()

        return holder

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]!!

        holder.binding.questionTextView.text = "${item.order+1}." +  " " +  item.question

        holder.binding.recyclerView.layoutManager = LinearLayoutManager(
            parent.requireContext()
        )
        if (item.answerType == QuestionAnswerType.OPTIONS)
            holder.binding.recyclerView.adapter = QuestionnaireAnswerRecyclerViewAdapter(
                item,
                viewModel,
                item.options!!, parent
            )
        else{
            holder.binding.recyclerView.visibility = View.GONE
            holder.binding.answerEditText.visibility = View.VISIBLE
            holder.binding.divider.visibility = View.GONE

            holder.binding.answerEditText.doOnTextChanged { text, start, before, count ->

                viewModel.setAnswerStrForQuestion(
                    item.id,
                    text.toString()
                )
            }
        }
//        holder.binding.root.setOnClickListener {
//
//            val args = MobileNavigationDirections.actionGlobalQuestionnaireAnswerScreen(
//                dayId,
//                item.id
//            )
//            parent.findNavController().navigate(args)
//        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: QuestionAnswerItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}