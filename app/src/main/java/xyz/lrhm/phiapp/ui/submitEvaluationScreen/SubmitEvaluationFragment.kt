package xyz.lrhm.phiapp.ui.submitEvaluationScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.core.data.source.AppRepository
import xyz.lrhm.phiapp.databinding.ParameterEvaluationItemBinding
import xyz.lrhm.phiapp.databinding.SubmitEvaluationFragmentBinding
import xyz.lrhm.phiapp.ui.util.bindTo
import javax.inject.Inject

@AndroidEntryPoint
class SubmitEvaluationFragment : Fragment() {

//    companion object {
//        fun newInstance() = SubmitEvaluationFragment()
//    }
//
//    private lateinit var viewModel: SubmitEvaluationViewModel

    val args by navArgs<SubmitEvaluationFragmentArgs>()

    @Inject lateinit var appRepository: AppRepository

    lateinit var binding: SubmitEvaluationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SubmitEvaluationFragmentBinding.inflate(inflater, container, false)


        val params = appRepository.getParametersForDay(args.exerciseParameterId)!!.parameters!!.filter {
            it?.enabled == true && it?.name.contains("rest") == false
        }


        binding.parameterEvalContainer.bindTo(params)

        return binding.root
    }



//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(SubmitEvaluationViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}