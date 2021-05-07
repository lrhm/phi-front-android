package xyz.lrhm.phiapp.ui.exerciseFragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.core.data.source.AppRepository
import xyz.lrhm.phiapp.databinding.FragmentExerciseBinding
import xyz.lrhm.phiapp.ui.util.bindTo
import javax.inject.Inject

@AndroidEntryPoint
class ExerciseFragment : Fragment() {

    lateinit var binding: FragmentExerciseBinding

    @Inject
    lateinit var appRepository: AppRepository

    lateinit var player: ExoPlayer

    val args: ExerciseFragmentArgs by navArgs()

    var isImagesSelected = true

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
        TabLayoutMediator(binding.tabLayout, binding.imageViewPager) { tab, position ->

        }.attach()

        binding.imageSelectButton.container.isSelected = true
        binding.imageSelectButton.imageView.isSelected = true
        binding.imageSelectButton.textView.isSelected = true

        binding.imageSelectButton.root.setOnClickListener {
            if(!isImagesSelected)
                toggleViews()
        }


        binding.videoSelectButton.root.setOnClickListener {
            if(isImagesSelected)
                toggleViews()
        }

        initExoPlayer(exercise.videos[0]!!.url.replace("localhost","192.168.2.5"))

        binding.parametersContainer.bindTo(exercise.parameters!!)

        binding.titleTextView.text = exercise.title

        binding.descriptionTextView.text = exercise.longDescription

        return binding.root
    }

    fun toggleViews(){
        isImagesSelected = !isImagesSelected

        if(isImagesSelected){

            if(player.isPlaying)
                player.stop()

            binding.imageSelectButton.container.isSelected = true
            binding.imageSelectButton.imageView.isSelected = true
            binding.imageSelectButton.textView.isSelected = true

            binding.videoSelectButton.container.isSelected = false
            binding.videoSelectButton.imageView.isSelected = false
            binding.videoSelectButton.textView.isSelected = false


            binding.playerView.visibility = View.INVISIBLE
            binding.imageViewPager.visibility = View.VISIBLE
            binding.tabLayout.visibility = View.VISIBLE
        }else{
            binding.imageSelectButton.container.isSelected = false
            binding.imageSelectButton.imageView.isSelected = false
            binding.imageSelectButton.textView.isSelected = false

            binding.videoSelectButton.container.isSelected = true
            binding.videoSelectButton.imageView.isSelected = true
            binding.videoSelectButton.textView.isSelected = true

            binding.playerView.visibility = View.VISIBLE
            binding.imageViewPager.visibility = View.INVISIBLE
            binding.tabLayout.visibility = View.INVISIBLE


        }
    }


    fun initExoPlayer(url: String) {
        player = SimpleExoPlayer.Builder(requireContext()).build()
        val dataSourceFactory = DefaultDataSourceFactory(
            requireContext(),
            Util.getUserAgent(requireContext(), "phiApp")
        )
        val mediaItem = MediaItem.fromUri(url)
        val mediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
        player!!.setMediaSource(mediaSource)
        player.prepare()

        binding.playerView!!.player = player

    }

    override fun onDestroy() {
        super.onDestroy()

        player.release()
    }

}