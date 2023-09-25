package com.packagename.mynotesapp.api

import com.packagename.mynotesapp.models.CreateArticleRequest
import com.packagename.mynotesapp.models.CreateArticleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ArticleApi {

    @POST("articles")
    suspend fun createArticle(@Body createArticleRequest: CreateArticleRequest) : Response<CreateArticleResponse>

    @GET("articles?limit=20")
    suspend fun getArticle() : Response<CreateArticleResponse>



}