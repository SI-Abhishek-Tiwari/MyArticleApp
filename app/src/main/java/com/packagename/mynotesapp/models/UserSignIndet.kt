package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class UserSignIndet(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)