package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class CreateArticleRequest(
    @SerializedName("article")
    val article: ArticleDet
)