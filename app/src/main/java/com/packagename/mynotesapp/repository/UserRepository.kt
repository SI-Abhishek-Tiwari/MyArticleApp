package com.packagename.mynotesapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.packagename.mynotesapp.api.UserApi
import com.packagename.mynotesapp.models.ErrorSignInResponse
import com.packagename.mynotesapp.models.ErrorSignUpResponse
import com.packagename.mynotesapp.models.UserResponse
import com.packagename.mynotesapp.models.UserSignInRequest
import com.packagename.mynotesapp.models.UserSignUpRequest
import com.packagename.mynotesapp.utilis.NetworkResult
import retrofit2.Response
import java.lang.StringBuilder
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()

    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData

    private val _userResponseSignInLivedata = MutableLiveData<NetworkResult<UserResponse>>()

    val userResponseSignInLivedata : LiveData<NetworkResult<UserResponse>>
        get() = _userResponseSignInLivedata


    suspend fun registerUser(userSignUpRequest: UserSignUpRequest){

        val response = userApi.signUp(userSignUpRequest)
        Log.d("abhi", response.body().toString())
        handleResponse(response)

    }

    suspend fun logInUser(userSignInRequest: UserSignInRequest){

        val response = userApi.signIn(userSignInRequest)
        Log.d("abhiz", response.body().toString())
        handleSignInResponse(response)

    }

    private fun handleResponse(response : Response<UserResponse>){
        if(response.isSuccessful && response.body() != null){
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }else if (response.errorBody() != null) {
            val gson = Gson()
            val my_error = StringBuilder()
            val errorResponse = gson.fromJson(
                response.errorBody()!!.charStream().readText(),
                ErrorSignUpResponse::class.java
            )

            if (errorResponse.errors != null) {
                if (errorResponse.errors.email != null && !errorResponse.errors.email.isEmpty()) {
                    my_error.append("Email ${errorResponse.errors.email[0]}")
                    my_error.appendLine()
                }
                if (errorResponse.errors.username != null && !errorResponse.errors.username.isEmpty()) {
                    my_error.append("UserName ${errorResponse.errors.username[0]}")
                }
            }
            _userResponseLiveData.postValue(NetworkResult.Error(my_error.toString()))
        }
        else{
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    private fun handleSignInResponse(response: Response<UserResponse>){
        if(response.isSuccessful && response.body() != null){
            _userResponseSignInLivedata.postValue(NetworkResult.Success(response.body()!!))
        }else if(response.errorBody() != null){
            val gson = Gson()
            val my_error = StringBuilder()
            val errorResponse = gson.fromJson(
                response.errorBody()!!.charStream().readText(),
                ErrorSignInResponse::class.java
            )
            if(errorResponse.errors != null){
                if(errorResponse.errors.emailOrPassword != null && !errorResponse.errors.emailOrPassword.isEmpty()){
                    my_error.append("Email or Password ${errorResponse.errors.emailOrPassword[0]}")
                    my_error.appendLine()
                }
            }
            _userResponseSignInLivedata.postValue(NetworkResult.Error(my_error.toString()))
        }
        else{
            _userResponseSignInLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

}