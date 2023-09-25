package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class ErrorsX(
    @SerializedName("email or password")
    val emailOrPassword: List<String>
)