package xyz.lrhm.phiapp.ui.exerciseFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.databinding.FragmentExerciseBinding

class ExerciseFragment : Fragment() {

    lateinit var binding: FragmentExerciseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentExerciseBinding.inflate(inflater, container, false)


//        val adapter = ScreenSlidePagerAdapter(this)
//        viewPager2.adapter = adapter
//        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
//
//        }.attach()

        return binding.root
    }

}