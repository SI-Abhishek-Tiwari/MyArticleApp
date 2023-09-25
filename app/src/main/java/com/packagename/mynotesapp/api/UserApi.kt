package com.packagename.mynotesapp.api

import com.packagename.mynotesapp.models.UserSignUpRequest
import com.packagename.mynotesapp.models.UserResponse
import com.packagename.mynotesapp.models.UserSignInRequest
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("users")
    suspend fun signUp(@Body userSignUpRequest: UserSignUpRequest) : Response<UserResponse>  //register

    @POST("users/login")
    suspend fun signIn(@Body userSignInRequest: UserSignInRequest) : Response<UserResponse>  //login

}