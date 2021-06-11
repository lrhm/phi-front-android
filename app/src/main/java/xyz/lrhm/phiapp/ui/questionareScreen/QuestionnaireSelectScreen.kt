package xyz.lrhm.phiapp.ui.questionareScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.databinding.FragmentQuestionareSelectScreenBinding
import xyz.lrhm.phiapp.ui.scheduleDayScreen.ExerciseScheduleRecyclerViewAdapter
import xyz.lrhm.phiapp.ui.scheduleDayScreen.ScheduleDayViewModel
import xyz.lrhm.phiapp.ui.submitEvaluationScreen.SubmitEvaluationFragmentArgs


@AndroidEntryPoint
class QuestionnaireSelectScreen : Fragment() {


    lateinit var binding: FragmentQuestionareSelectScreenBinding
    val viewModel: QuestionnaireViewModel by viewModels({ requireActivity() })
    val args by navArgs<QuestionnaireSelectScreenArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQuestionareSelectScreenBinding.inflate(inflater, container, false)

        viewModel.loadForDay(args.dayId)


        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)

            val list = viewModel.getQuestionnairesForDayId(args.dayId).filter { it?.answered == false }

            adapter = QuestionnaireSelectRecyclerViewAdapter(
                args.dayId,
                list,
                this@QuestionnaireSelectScreen,

                )

        }

        return binding.root

    }

    override fun onResume() {
        super.onResume()

        try {
            if (viewModel.questionnaires.value?.map { it!!.answered!! }
                    ?.reduce { acc, b -> b && acc } == true)
                findNavController().navigateUp()

        } catch (e: Exception) {

        }

    }

}