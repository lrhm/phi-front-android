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
import dagger.hilt.android.AndroidEntryPoint
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.core.data.source.AppRepository
import xyz.lrhm.phiapp.databinding.PainSelectorItemBinding
import xyz.lrhm.phiapp.databinding.ParameterEvaluationItemBinding
import xyz.lrhm.phiapp.databinding.SubmitEvaluationFragmentBinding
import xyz.lrhm.phiapp.ui.scheduleDayScreen.ScheduleDayViewModel
import xyz.lrhm.phiapp.ui.util.bindTo
import xyz.lrhm.phiapp.ui.util.getWidth
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SubmitEvaluationFragmentBinding.inflate(inflater, container, false)

        viewModel.load(args.exerciseParameterId)

        viewModel.parameters.observe(viewLifecycleOwner) { params ->
            binding.parameterEvalContainer.bindTo(params, viewModel.parameters)

        }

        generatePainSelectors(inflater)

//        (binding.test.drawable as GradientDrawable).setStroke(4, Color.RED)
        return binding.root
    }


    fun generatePainSelectors(inflater: LayoutInflater) {
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
            painSelector.textView.text= "${i}"
            painSelector.textView.setTextColor(selectedColor)
            Glide.with(this).load(R.drawable.bordered_circle).into(painSelector.imageView)

            painSelector.root.setOnClickListener {

                val prevSelected = viewModel.selectedPainValue.value
                if(prevSelected != -1){
                    Glide.with(this).load(R.drawable.bordered_circle).into(painSelectorList[prevSelected!!].imageView)
                    painSelectorList[prevSelected!!].textView.setTextColor(Color.parseColor(colorListValues[prevSelected]))
                }

                Glide.with(this).load(R.drawable.selected_bordered_circle).into(painSelector.imageView)
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