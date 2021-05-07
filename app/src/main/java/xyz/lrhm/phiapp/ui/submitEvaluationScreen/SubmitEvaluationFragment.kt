package xyz.lrhm.phiapp.ui.submitEvaluationScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.lrhm.phiapp.R

class SubmitEvaluationFragment : Fragment() {

//    companion object {
//        fun newInstance() = SubmitEvaluationFragment()
//    }
//
//    private lateinit var viewModel: SubmitEvaluationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.submit_evaluation_fragment, container, false)
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(SubmitEvaluationViewModel::class.java)
//        // TODO: Use the ViewModel
//    }

}