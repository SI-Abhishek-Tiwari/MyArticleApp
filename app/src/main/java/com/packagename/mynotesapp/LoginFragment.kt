package com.packagename.mynotesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.packagename.mynotesapp.databinding.FragmentLoginBinding
import com.packagename.mynotesapp.models.UserSignInRequest
import com.packagename.mynotesapp.models.UserSignIndet
import com.packagename.mynotesapp.models.UserSignUpRequest
import com.packagename.mynotesapp.models.UserSignUpdet
import com.packagename.mynotesapp.utilis.NetworkResult
import com.packagename.mynotesapp.utilis.TokenManager
import com.packagename.mynotesapp.utilis.Username
import com.packagename.mynotesapp.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    @Inject
    lateinit var username: Username

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container,false)

        if(tokenManager.getToken() != null){
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        bindObserver()

    }


    private fun userRequest(): UserSignInRequest {
        val emailAddress = binding.edtEmail.text.toString()
        val password = binding.edtPsd.text.toString()
        return UserSignInRequest(UserSignIndet(emailAddress,password))
    }

    private fun setUpUi(){
        binding.btnSignIn.setOnClickListener {
            //   authViewModel.logInUser(UserSignInRequest(UserSignIndet("abdlf@gmail.com","1235689")))
            authViewModel.logInUser(userRequest())

        }

        binding.btnSignUp.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun bindObserver(){
        authViewModel.userResponseSignInLivedata.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Error -> {

                    binding.txtError.text = it.message
                }
                is NetworkResult.Loading -> TODO()
                is NetworkResult.Success -> {
                    tokenManager.saveToken(it?.data?.user!!.token)
                    username.saveUsername(it.data.user.username)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}