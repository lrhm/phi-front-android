package xyz.lrhm.phiapp.ui.splashScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.core.data.source.AppRepository
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    @Inject
    lateinit var appRepository: AppRepository

    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        // TODO: Use the ViewModel


        lifecycleScope.launch {
            delay(500)
            if(!appRepository.isLoggedIn())
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            else{
                lifecycleScope.launch {

                    val user = appRepository.remoteDataSource.getUser()

                    Timber.d("user iz $user")
//                    findNavController().navigate(R.id.action_splashFragment_to_exerciseGalleryFragment)



                }
            }
        }
    }

}