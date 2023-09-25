package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class UserSignInRequest(
    @SerializedName("user")
    val user: UserSignIndet
)