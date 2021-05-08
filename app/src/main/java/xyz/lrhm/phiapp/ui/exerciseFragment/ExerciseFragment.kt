package xyz.lrhm.phiapp.ui.exerciseFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.danikula.videocache.HttpProxyCacheServer
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import xyz.lrhm.phiapp.MobileNavigationDirections
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.core.data.source.AppRepository
import xyz.lrhm.phiapp.databinding.FragmentExerciseBinding
import xyz.lrhm.phiapp.ui.util.bindTo
import xyz.lrhm.type.ExerciseType
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

        initExoPlayer(exercise.videos[0]!!.url.replace("localhost", "192.168.2.5"))

//        toggleViews()

        binding.evaluationButton.visibility = View.GONE
        if(args.exerciseParameterId != null){
            binding.evaluationButton.visibility = View.VISIBLE

            val params = appRepository.getParametersForDay(args.exerciseParameterId!!)!!
//            binding.parametersContainer.bindTo(params.parameters!!)
        }
        else if (exercise.type == ExerciseType.EXERCISE){}
//        binding.parametersContainer.bindTo(exercise.parameters!!)

        binding.titleTextView.text = exercise.title

        binding.descriptionTextView.text = exercise.longDescription



        binding.evaluationButton.setOnClickListener {

            val direction = MobileNavigationDirections.actionGlobalSubmitEvaluationFragment(args.exerciseParameterId!!)
            findNavController().navigate(R.id.action_global_submitEvaluationFragment)
        }

        return binding.root
    }

    fun toggleViews() {
        isImagesSelected = !isImagesSelected

        if (isImagesSelected) {

            if (player.isPlaying)
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
        } else {
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

        Timber.d("init exoo")

        val proxyServer =
            HttpProxyCacheServer.Builder(context).maxCacheSize(1024 * 1024 * 1024).build()

        val proxyURL = proxyServer.getProxyUrl(url)


        Timber.d("proxy url ${proxyURL}")
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSourceFactory()

        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(proxyURL))

        player = SimpleExoPlayer.Builder(requireContext()).build()

        player.setMediaItem(MediaItem.fromUri(proxyURL))
//        player.setMediaSource(mediaSource)

        player.prepare()

//        binding.playerView.player = player


        binding.playerView!!.player = player

    }

    override fun onDestroy() {
        super.onDestroy()

        player.release()
    }

}