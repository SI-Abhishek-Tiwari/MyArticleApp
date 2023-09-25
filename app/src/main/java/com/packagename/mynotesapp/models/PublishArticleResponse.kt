package com.packagename.mynotesapp.models


import com.google.gson.annotations.SerializedName

data class PublishArticleResponse(
    @SerializedName("errors")
    val errors: ErrorsDet
)