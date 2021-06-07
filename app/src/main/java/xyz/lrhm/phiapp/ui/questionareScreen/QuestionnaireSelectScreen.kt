package xyz.lrhm.phiapp.ui.questionareScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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

        args.dayId

        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)

            val list = viewModel.getQuestionnairesForDayId(args.dayId)

            adapter = QuestionnaireSelectRecyclerViewAdapter(
                list,
             this@QuestionnaireSelectScreen,

            )

        }

        return binding.root

    }

}