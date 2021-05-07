package xyz.lrhm.phiapp.ui.exerciseFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.core.data.source.AppRepository
import xyz.lrhm.phiapp.databinding.FragmentExerciseBinding
import javax.inject.Inject

@AndroidEntryPoint
class ExerciseFragment : Fragment() {

    lateinit var binding: FragmentExerciseBinding

    @Inject
    lateinit var appRepository: AppRepository

    val args: ExerciseFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentExerciseBinding.inflate(inflater, container, false)

        val exercise = appRepository.getExercise(args.exerciseId)

        val list = exercise.pictures.toMutableList()
            list.addAll(exercise.pictures)
        list.addAll(list)
        val adapter = ExerciseImagesRecyclerViewAdapter(list)
        binding.imageViewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.imageViewPager){ tab, position ->

        }.attach()

        binding.imageSelectButton.container.isSelected = true
        binding.imageSelectButton.imageView.isSelected = true

//        binding.imageSelectButton.container.isSelected = true
//
//        binding.imageSelectButton.container.isSelected = true


//        val adapter = ScreenSlidePagerAdapter(this)
//        viewPager2.adapter = adapter
//        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
//
//        }.attach()

        return binding.root
    }

}