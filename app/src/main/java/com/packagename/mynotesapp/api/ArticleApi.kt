package com.packagename.mynotesapp.api

import com.packagename.mynotesapp.models.CreateArticleRequest
import com.packagename.mynotesapp.models.CreateArticleResponse
import com.packagename.mynotesapp.models.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleApi {

    @POST("articles")
    suspend fun createArticle(@Body createArticleRequest: CreateArticleRequest) : Response<CreateArticleResponse>

    @GET("articles?limit=20")
    suspend fun getArticle() : Response<CreateArticleResponse>


    @GET("articles?limit=20")
    suspend fun getArticleByUsername(
        @Query("author") username:String
    ) : Response<CreateArticleResponse>

    @GET("articles?limit=20")
    suspend fun getFavouriteArticleByUsername(
        @Query("favorited") username: String
    ) : Response<CreateArticleResponse>

    @GET("profiles/{username}")
    suspend fun getProfile(
        @Path("username") username:String
    ) : Response<ProfileResponse>

    @POST("articles/{slug}/favorite")
    suspend fun addFavourite(
        @Path("slug") slug:String
    ) : Response<CreateArticleResponse>

    @DELETE("articles/{slug}/favorite")
    suspend fun deleteFromFavourite(
        @Path("slug") slug:String
    ) : Response<CreateArticleResponse>





}