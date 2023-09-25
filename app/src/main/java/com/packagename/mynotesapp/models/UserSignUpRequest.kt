package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class UserSignUpRequest(
    @SerializedName("user")
    val user: UserSignUpdet
)