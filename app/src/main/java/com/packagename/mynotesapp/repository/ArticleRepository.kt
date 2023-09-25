package com.packagename.mynotesapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.packagename.mynotesapp.api.ArticleApi

import com.packagename.mynotesapp.models.CreateArticleRequest
import com.packagename.mynotesapp.models.CreateArticleResponse
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