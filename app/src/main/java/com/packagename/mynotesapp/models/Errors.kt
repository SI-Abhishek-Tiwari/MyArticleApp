package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class Errors(
    @SerializedName("email")
    val email: List<String>,
    @SerializedName("username")
    val username: List<String>
)