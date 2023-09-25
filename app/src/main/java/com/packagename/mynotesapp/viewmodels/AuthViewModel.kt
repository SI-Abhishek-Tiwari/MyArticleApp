package com.packagename.mynotesapp.viewmodels

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packagename.mynotesapp.models.UserResponse
import com.packagename.mynotesapp.models.UserSignInRequest
import com.packagename.mynotesapp.models.UserSignUpRequest
import com.packagename.mynotesapp.repository.UserRepository
import com.packagename.mynotesapp.utilis.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData

    val userResponseSignInLivedata : LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseSignInLivedata

    fun registerUser(userSignUpRequest: UserSignUpRequest){
        viewModelScope.launch {
            userRepository.registerUser(userSignUpRequest)
        }
    }

    fun logInUser(userSignInRequest: UserSignInRequest){
        viewModelScope.launch {
            userRepository.logInUser(userSignInRequest)
        }
    }

     fun validateCredential(username : String,password : String,email:String,isLogin: Boolean): Pair<Boolean,String>{
        var result = Pair(true," ")
        if(!isLogin && TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)){
             result = Pair(false,"PLease Enter the credential")
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            result = Pair(false,"Enter the valid email")
        }
        else if(password.length <= 5){
            result = Pair(false,"Enter password More Than % digit")
        }
        return result

    }


}