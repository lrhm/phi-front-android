package xyz.lrhm.phiapp.ui.exerciseGalleryScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import xyz.lrhm.phiapp.core.data.source.AppRepository
import xyz.lrhm.phiapp.core.util.CacheUtil
import xyz.lrhm.phiapp.databinding.FragmentExerciseGalleryBinding
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */

@AndroidEntryPoint
class ExerciseGalleryFragment : Fragment() {

    private var columnCount = 1

    lateinit var binding: FragmentExerciseGalleryBinding

    @Inject
    lateinit var appRepository: AppRepository

    @Inject
    lateinit var cacheUtil: CacheUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentExerciseGalleryBinding.inflate(inflater, container, false)
        // Set the adapter

            with(binding.recyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                appRepository.user.observe(viewLifecycleOwner){
                  val items=  it!!.patient!!.schedule!!.exercises!!

                    adapter = ExerciseGalleryRecyclerViewAdapter(items, this@ExerciseGalleryFragment)

                }
            }

        return binding.root
    }


}