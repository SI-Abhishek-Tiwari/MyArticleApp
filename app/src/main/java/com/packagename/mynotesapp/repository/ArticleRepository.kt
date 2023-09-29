package com.packagename.mynotesapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.packagename.mynotesapp.api.ArticleApi

import com.packagename.mynotesapp.models.CreateArticleRequest
import com.packagename.mynotesapp.models.CreateArticleResponse
import com.packagename.mynotesapp.models.ProfileResponse
import com.packagename.mynotesapp.models.PublishArticleResponse
import com.packagename.mynotesapp.utilis.NetworkResult
import retrofit2.Response
import java.lang.StringBuilder
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val articleApi: ArticleApi) {

    private val _articleResponseLivedata = MutableLiveData<NetworkResult<CreateArticleResponse>>()
    val articleResponseLivedata : LiveData<NetworkResult<CreateArticleResponse>>
        get() = _articleResponseLivedata

    private val _statusArticleLivedata = MutableLiveData<NetworkResult<CreateArticleResponse>>()
    val statusArticleLivedata : LiveData<NetworkResult<CreateArticleResponse>>
        get() = _statusArticleLivedata

    private val _articleByUsernameLivedata = MutableLiveData<NetworkResult<CreateArticleResponse>>()
    val articleByUsernameLivedata : LiveData<NetworkResult<CreateArticleResponse>>
        get() = _articleByUsernameLivedata

   private val _profileLiveData = MutableLiveData<NetworkResult<ProfileResponse>>()
        val profileLiveData : LiveData<NetworkResult<ProfileResponse>>
            get() = _profileLiveData

    private val _favouriteArticle = MutableLiveData<NetworkResult<CreateArticleResponse>>()
        val favouriteArticle : LiveData<NetworkResult<CreateArticleResponse>>
            get() = _favouriteArticle

   suspend fun createArticle(createArticleRequest: CreateArticleRequest){
       val response = articleApi.createArticle(createArticleRequest)
       Log.e("article",  response.body().toString())
       handleResponse(response)

    }

    suspend fun getArticle()  {
        val response = articleApi.getArticle()
        if (response.isSuccessful && response.body() != null){
            Log.e("article get", response.body().toString())
            _articleResponseLivedata.postValue(NetworkResult.Success(response.body()!!))
        }else{
            _articleResponseLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun getArticleUsername(username: String) {
        val response = articleApi.getArticleByUsername(username)
        if (response.isSuccessful && response.body() != null){
            Log.d("article by username", response.body().toString())
            _articleByUsernameLivedata.postValue(NetworkResult.Success(response.body()!!))
        }else{
            _articleByUsernameLivedata.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun getFavouriteArticleByUsername(username: String){
        val response = articleApi.getFavouriteArticleByUsername(username)
        if (response.isSuccessful && response.body() != null){
            Log.d("favourite Article", response.body()!!.articles.size.toString())
            _favouriteArticle.postValue(NetworkResult.Success(response.body()!!))
        }else{
            _favouriteArticle.postValue((NetworkResult.Error("Something went wrong")))
        }
    }

    suspend fun getProfile(username: String) {
        val response = articleApi.getProfile(username)
        if(response.isSuccessful && response.body() != null){
            _profileLiveData.postValue(NetworkResult.Success(response.body()!!))
        }
        else{
            _profileLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun addFavourite(slug : String){
        val response = articleApi.addFavourite(slug)
        if (response.isSuccessful && response.body() != null){
            Log.d("add favourite", response.body()?.articles?.size.toString())
        }
    }

    suspend fun deleteFromFavourite(slug: String){
        val response = articleApi.deleteFromFavourite(slug)
        if (response.isSuccessful && response.body() != null){
            Log.d("delete favourite", response.body()?.articles?.size.toString())
        }
    }

    private  fun handleResponse(response: Response<CreateArticleResponse>){

        if(response.isSuccessful && response.body() != null){
            _statusArticleLivedata.postValue(NetworkResult.Success(response.body()!!))
        }
        else if(response.errorBody() != null){
            val gson = Gson()
            val my_error = StringBuilder()
            val errorResponse = gson.fromJson(
                response.errorBody()!!.charStream().readText(),
                PublishArticleResponse::class.java
            )
            if(errorResponse.errors != null){
                if (errorResponse.errors.title != null && !errorResponse.errors.title.isEmpty()) {
                    my_error.append("Title ${errorResponse.errors.title[0]}")
                    my_error.appendLine()
                }
            }

            _statusArticleLivedata.postValue(NetworkResult.Error(my_error.toString()))
        }
        else{
            _statusArticleLivedata.postValue(NetworkResult.Error("Something get wrong"))
        }
    }

}