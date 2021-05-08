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
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import xyz.lrhm.APIQuery
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.core.data.source.AppRepository
import xyz.lrhm.phiapp.databinding.ParameterEvaluationItemBinding
import xyz.lrhm.phiapp.databinding.SubmitEvaluationFragmentBinding
import xyz.lrhm.phiapp.ui.scheduleDayScreen.ScheduleDayViewModel
import xyz.lrhm.phiapp.ui.util.bindTo
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SubmitEvaluationFragmentBinding.inflate(inflater, container, false)

        viewModel.load(args.exerciseParameterId)

        viewModel.parameters.observe(viewLifecycleOwner) { params ->
            binding.parameterEvalContainer.bindTo(params, viewModel.parameters)

        }

        (binding.test.drawable as GradientDrawable).setStroke(4, Color.RED)
        return binding.root
    }


//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(SubmitEvaluationViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}