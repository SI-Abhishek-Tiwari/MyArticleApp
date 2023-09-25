package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("user")
    val user: User?,

)