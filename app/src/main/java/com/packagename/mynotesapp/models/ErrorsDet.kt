package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class ErrorsDet(
    @SerializedName("title")
    val title: List<String>
)