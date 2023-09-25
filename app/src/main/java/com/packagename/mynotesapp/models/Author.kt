package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("bio")
    val bio: Any,
    @SerializedName("following")
    val following: Boolean,
    @SerializedName("image")
    val image: String,
    @SerializedName("username")
    val username: String
)