package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class ErrorSignUpResponse(
    @SerializedName("errors")
    val errors: Errors
)