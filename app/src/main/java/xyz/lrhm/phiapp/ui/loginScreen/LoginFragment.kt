package xyz.lrhm.phiapp.ui.loginScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import xyz.lrhm.phiapp.R
import xyz.lrhm.phiapp.core.data.model.ResultOf
import xyz.lrhm.phiapp.databinding.LoginFragmentBinding

@AndroidEntryPoint
class LoginFragment : Fragment() {



    val viewModel: LoginViewModel by viewModels({requireActivity()})
    lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        viewModel.login("test", "test")

       binding = LoginFragmentBinding.inflate(inflater , container, false)

        binding.loginButton.setOnClickListener {

            val userName = binding.userNameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()


            if(userName == "" || password == "")
                Toast.makeText(requireContext(), "اطلاعات را کامل کنید", Toast.LENGTH_LONG).show()
            else
                viewModel.login(userName, password)
        }

        viewModel.loginResult.observe(viewLifecycleOwner){

            if(it is ResultOf.Error){
                Toast.makeText(requireContext(), "نام کاربری یا رمز ورود اشتباه است", Toast.LENGTH_LONG).show()

            }
        }
        viewModel.loggedIn.observe(viewLifecycleOwner){

            if(it){
                findNavController().navigate(R.id.action_global_scheduleDayFragment2)

            }
        }

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}