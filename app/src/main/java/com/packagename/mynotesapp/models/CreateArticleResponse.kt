package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class CreateArticleResponse(
    @SerializedName("articles")
    val articles: List<ArticleDto>
)