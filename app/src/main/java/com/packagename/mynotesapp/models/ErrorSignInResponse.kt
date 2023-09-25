package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class ErrorSignInResponse(
    @SerializedName("errors")
    val errors: ErrorsX
)