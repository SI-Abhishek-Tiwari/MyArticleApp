package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class ArticleDet(
    @SerializedName("body")
    val body: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("title")
    val title: String
)

//@SerializedName("tagList")
//val tagList: List<String>,