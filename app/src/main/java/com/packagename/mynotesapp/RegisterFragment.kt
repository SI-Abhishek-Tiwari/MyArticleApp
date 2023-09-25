package com.packagename.mynotesapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.packagename.mynotesapp.databinding.FragmentRegisterBinding
import com.packagename.mynotesapp.models.UserSignUpdet
import com.packagename.mynotesapp.models.UserSignUpRequest
import com.packagename.mynotesapp.utilis.NetworkResult
import com.packagename.mynotesapp.utilis.TokenManager
import com.packagename.mynotesapp.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        if(tokenManager.getToken() != null){
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.btnSignUp.setOnClickListener {
//            authViewModel.registerUser(UserSignUpRequest(UserSignUpdet("ggfkkgf@gmail.com","1235689","mggncsfeddd")))
////            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
//
//        }

        binding.btnSignUp.setOnClickListener {
            val validationResult = validateUserInput()
            if(validationResult.first){
                authViewModel.registerUser(userRequest())
            }
            else{
                binding.txtError.text = validationResult.second
            }
        }

        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        observer()
    }

    private fun userRequest(): UserSignUpRequest{
        val emailAddress = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        val username = binding.edtUsername.text.toString()
        return  UserSignUpRequest(UserSignUpdet(emailAddress,password,username))
    }

    private fun validateUserInput() : Pair<Boolean,String>{
        val userRequest = userRequest().user
         return   authViewModel.validateCredential(userRequest.username,userRequest.password,userRequest.email,true)
    }




    private fun observer(){

        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Success -> {
                    //Token
                    tokenManager.saveToken(it?.data?.user!!.token)
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }
                is NetworkResult.Error -> {

                    binding.txtError.text = it.message
                }
                is NetworkResult.Loading -> {

                }

            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}