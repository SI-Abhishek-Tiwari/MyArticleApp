package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("profile")
    val profile: Profile
)