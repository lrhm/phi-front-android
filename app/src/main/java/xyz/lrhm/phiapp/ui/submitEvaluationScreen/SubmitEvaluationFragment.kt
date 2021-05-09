package xyz.lrhm.phiapp.ui.submitEvaluationScreen

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.GradientDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.setMargins
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.core.data.source.AppRepository
import xyz.lrhm.phiapp.databinding.DifficultySelectorItemBinding
import xyz.lrhm.phiapp.databinding.PainSelectorItemBinding
import xyz.lrhm.phiapp.databinding.ParameterEvaluationItemBinding
import xyz.lrhm.phiapp.databinding.SubmitEvaluationFragmentBinding
import xyz.lrhm.phiapp.ui.scheduleDayScreen.ScheduleDayViewModel
import xyz.lrhm.phiapp.ui.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SubmitEvaluationFragment : Fragment() {

    //    companion object {
//        fun newInstance() = SubmitEvaluationFragment()
//    }
//
//    private lateinit var viewModel: SubmitEvaluationViewModel
    val viewModel: SubmitEvaluationViewModel by viewModels({ this })

    val args by navArgs<SubmitEvaluationFragmentArgs>()

    lateinit var binding: SubmitEvaluationFragmentBinding

    val painSelectorList = mutableListOf<PainSelectorItemBinding>()
    val difficultySelectorList = mutableListOf<DifficultySelectorItemBinding>()
    val fatigueSelectorList = mutableListOf<DifficultySelectorItemBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SubmitEvaluationFragmentBinding.inflate(inflater, container, false)

        viewModel.load(args.exerciseParameterId)

        viewModel.parameters.observe(viewLifecycleOwner) { params ->
            binding.parameterEvalContainer.bindTo(params, viewModel.parameters)
        }

        viewModel.exercise.observe(viewLifecycleOwner) { exercise ->

            Timber.d("assesments are ${exercise.assesments}")

            if(exercise.assesments.isPainEnabled())
                generatePainSelectors(inflater)

            if(exercise.assesments.isFatigueEnabled())
                generateFatigueSelectors(inflater)

            if(exercise.assesments.isDifficultyEnabled())
                generateDifficultySelectors(inflater)

        }



//        (binding.test.drawable as GradientDrawable).setStroke(4, Color.RED)
        return binding.root
    }

    fun generateFatigueSelectors(inflater: LayoutInflater) {
        Timber.d("generate fatigue selectors")

        binding.exerciseFatigueContainer.visibility = View.VISIBLE

        val colorListValues = listOf(

            "#00609d",
            "#80ac01",
            "#d2ab10",
            "#f38607",
            "#d5393a",

            )

        val labels = listOf(
            "خیلی آسان",
            "آسان",
            "کمی سخت",
            "سخت",
            "خیلی سخت"
        )
        val size = requireContext().getWidth() * 0.16
        val margin = size * 0.05

        for (i in 0..4) {
            val layoutParams = FlexboxLayout.LayoutParams(size.toInt(), size.toInt())
            layoutParams.setMargins(margin.toInt())
            val difficultySelector = DifficultySelectorItemBinding.inflate(inflater)
            fatigueSelectorList.add(difficultySelector)

            binding.fatigueSelectorContainer.addView(difficultySelector.root, layoutParams)

            var valueText = "${i + 1}"


            difficultySelector.imageView.alpha = 0.35f
            difficultySelector.imageView.setColorFilter(Color.parseColor(colorListValues[i]))
            difficultySelector.valueTextView.text = valueText
            difficultySelector.labelTextView.text = labels[i]

            difficultySelector.root.setOnClickListener {

                val prevSelectedItem = viewModel.selectedFatigueValue.value
                if (prevSelectedItem != -1) {
                    fatigueSelectorList[prevSelectedItem!!].imageView.alpha = 0.35f
                }

                difficultySelector.imageView.alpha = 1.0f
                viewModel.selectedFatigueValue.value = i
            }


        }
    }

    fun generateDifficultySelectors(inflater: LayoutInflater) {

        binding.exerciseDifficultyContainer.visibility = View.VISIBLE
        val colorListValues = listOf(

            "#0164a5",
            "#0063a4",
            "#88b500",
            "#89b700",
            "#88b500",
            "#e5ba13",
            "#e5ba13",
            "#e27e06",
            "#e88005",
            "#df3c3b"
        )

        val labels = listOf(
            "هیچ",
            "خیلی کم",
            "کم",
            "متوسط",
            "کمی شدید",
            "شدید",
            "شدید",
            "خیلی شدید",
            "خیلی شدید",
            "ماکسیمم",
        )


        val size = requireContext().getWidth() * 0.16
        val margin = size * 0.05

        for (i in 0..9) {
            val layoutParams = FlexboxLayout.LayoutParams(size.toInt(), size.toInt())
            layoutParams.setMargins(margin.toInt())
            val difficultySelector = DifficultySelectorItemBinding.inflate(inflater)
            difficultySelectorList.add(difficultySelector)

            binding.difficultySelectorContainer.addView(difficultySelector.root, layoutParams)

            var valueText = "$i"
            if (i == 9) {
                valueText = "۹،۱۰"
            }

            difficultySelector.imageView.alpha = 0.35f
            difficultySelector.imageView.setColorFilter(Color.parseColor(colorListValues[i]))
            difficultySelector.valueTextView.text = valueText
            difficultySelector.labelTextView.text = labels[i]

            difficultySelector.root.setOnClickListener {

                val prevSelectedItem = viewModel.selectedDifficulty.value
                if (prevSelectedItem != -1) {
                    difficultySelectorList[prevSelectedItem!!].imageView.alpha = 0.35f
                }

                difficultySelector.imageView.alpha = 1.0f
                viewModel.selectedDifficulty.value = i
            }


        }

    }

    fun generatePainSelectors(inflater: LayoutInflater) {

        binding.painContainer.visibility = View.VISIBLE
        val colorListValues = listOf(
            "#2d437c",
            "#27866a",
            "#54a456",
            "#8cb661",
            "#afbf6e",
            "#d1d84f",
            "#e7d139",
            "#d78f3c",
            "#dc5e30",
            "#b8232b",
            "#a5201b"
        )
        val imageSize = requireContext().getWidth() * 0.0736
        val margin = imageSize * 0.1

        for (i in 0..10) {
            val layoutParams = LinearLayout.LayoutParams(imageSize.toInt(), imageSize.toInt())
            layoutParams.setMargins(margin.toInt())


            val painSelector = PainSelectorItemBinding.inflate(inflater)
            val selectedColor = Color.parseColor(colorListValues[i])

            binding.painSelectorContainer.addView(painSelector.root, layoutParams)
            painSelectorList.add(painSelector)
            painSelector.imageView.setColorFilter(selectedColor)
            painSelector.textView.text = "${i}"
            painSelector.textView.setTextColor(selectedColor)
            Glide.with(this).load(R.drawable.bordered_circle).override(
                imageSize.toInt(), imageSize.toInt()
            ).into(painSelector.imageView)

            painSelector.root.setOnClickListener {

                val prevSelected = viewModel.selectedPainValue.value
                if (prevSelected != -1) {
                    Glide.with(this).load(R.drawable.bordered_circle).override(
                        imageSize.toInt(), imageSize.toInt()
                    )
                        .into(painSelectorList[prevSelected!!].imageView)
                    painSelectorList[prevSelected!!].textView.setTextColor(
                        Color.parseColor(
                            colorListValues[prevSelected]
                        )
                    )
                }

                Glide.with(this).load(R.drawable.selected_bordered_circle)
                    .into(painSelector.imageView)
                painSelector.textView.setTextColor(Color.WHITE)


                viewModel.selectedPainValue.value = i
            }

        }

    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(SubmitEvaluationViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}