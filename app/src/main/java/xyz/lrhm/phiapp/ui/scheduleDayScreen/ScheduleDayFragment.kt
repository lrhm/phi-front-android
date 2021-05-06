package xyz.lrhm.phiapp.ui.scheduleDayScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.core.util.isSameDay
import xyz.lrhm.phiapp.databinding.FragmentScheduleDayBinding
import xyz.lrhm.phiapp.ui.loginScreen.LoginViewModel

@AndroidEntryPoint
class ScheduleDayFragment : Fragment() {

//    val args: ScheduleDayFragmentArgs by navArgs()

    lateinit var binding: FragmentScheduleDayBinding
    val viewModel: ScheduleDayViewModel by viewModels({requireActivity()})

    val formatter = PersianDateFormat("j F Y")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScheduleDayBinding.inflate(inflater, container, false)
        viewModel.selectedDate.observe(viewLifecycleOwner){
            val today = PersianDate()
            if(today.isSameDay(it))
            binding.dateBoldText.text=    "امروز" + " " + it.dayName() + " "
            else{
                binding.dateBoldText.text=      it.dayName() + " "

            }
            binding.dateTextView.text = formatter.format(it)
        }


        return binding.root
    }


}