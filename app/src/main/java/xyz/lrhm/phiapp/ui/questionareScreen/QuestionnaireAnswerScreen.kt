package xyz.lrhm.phiapp.ui.questionareScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import xyz.lrhm.phiapp.MainActivity
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.core.data.model.ResultOf
import xyz.lrhm.phiapp.databinding.FragmentQuestionareAnswerBinding
import xyz.lrhm.phiapp.databinding.FragmentQuestionareSelectScreenBinding
import xyz.lrhm.phiapp.ui.scheduleDayScreen.ExerciseScheduleRecyclerViewAdapter
import xyz.lrhm.phiapp.ui.scheduleDayScreen.ScheduleDayViewModel
import xyz.lrhm.phiapp.ui.submitEvaluationScreen.SubmitEvaluationFragmentArgs


@AndroidEntryPoint
class QuestionnaireAnswerScreen : Fragment() {


    lateinit var binding: FragmentQuestionareAnswerBinding
    val viewModel: QuestionnaireViewModel by viewModels({ requireActivity() })
    val args by navArgs<QuestionnaireAnswerScreenArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQuestionareAnswerBinding.inflate(inflater, container, false)


        val questionnaire = viewModel.getQuestionnaireForId(args.questionnaireId)

        viewModel.initAnswerMapForQuestionnaire(args.questionnaireId)

        binding.questionCount.text = "${questionnaire.questions!!.size} سوال "
//        requireActivity().title = questionnaire.title

        (requireActivity() as MainActivity).supportActionBar?.title = questionnaire.title
        activity?.title = questionnaire.title

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = QuestionnaireRecyclerViewAdapter(
                viewModel,
                questionnaire.questions!!,
                this@QuestionnaireAnswerScreen
            )


//            val list = viewModel.getQuestionnairesForDayId(args.dayId)
//
//            adapter = QuestionnaireSelectRecyclerViewAdapter(
//                list,
//             this@QuestionnaireAnswerScreen,
//
//            )


        }


        binding.submitEvaluationButton.setOnClickListener {

            Timber.d("Answres are , ${viewModel.isAllAnswered()}")

            if (viewModel.isAllAnswered().not()) {
                binding.errorTextView.text = "اطلاعات کامل نیست، لطفا مقادیر را انتخواب کنید"

            } else {
                binding.errorTextView.text = ""
                viewModel.sendQuestionnaireAnswer(
                    args.dayId,
                    args.questionnaireId
                )
                binding.submitEvaluationButton.startAnimation()

            }


        }

        viewModel.result.observe(viewLifecycleOwner){

            Timber.d("result is ${it}")
            if (it is ResultOf.Error) {
                binding.errorTextView.text = "خطا رخ داده است، از اتصال به اینترنت مطمئن شوید."
                binding.submitEvaluationButton.revertAnimation {

                }

            }
            else{
                binding.submitEvaluationButton.revertAnimation {
                    binding.submitEvaluationButton.text = "ارسال شد"

                    val drawable = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.submit_btn_drawable
                    )!!
                    val color = ContextCompat.getColor(requireContext(), R.color.success)
                    DrawableCompat.setTint(drawable, color)

                    binding.submitEvaluationButton.background = drawable

                }
            }
        }
        return binding.root

    }

}