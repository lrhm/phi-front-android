package xyz.lrhm.phiapp.ui.scheduleDayScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.databinding.FragmentScheduleDayBinding

class ScheduleDayFragment : Fragment() {

//    val args: ScheduleDayFragmentArgs by navArgs()

    lateinit var binding: FragmentScheduleDayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScheduleDayBinding.inflate(inflater, container, false)



        return binding.root
    }


}