package com.packagename.mynotesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.packagename.mynotesapp.models.CreateArticleRequest
import com.packagename.mynotesapp.models.CreateArticleResponse
import com.packagename.mynotesapp.models.UserResponse
import com.packagename.mynotesapp.repository.ArticleRepository
import com.packagename.mynotesapp.utilis.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val articleRepository: ArticleRepository) : ViewModel() {

//     val articleResponseLivedata : LiveData<NetworkResult<List<CreateArticleResponse>>>
//        get() = articleRepository.articleResponseLivedata

    val articleResponseLivedata get() = articleRepository.articleResponseLivedata
    val statusArticleLiveData get() = articleRepository.statusArticleLivedata

    val articleByUsernameLivedata get() = articleRepository.articleByUsernameLivedata

    val profileLiveData get() = articleRepository.profileLiveData

    val favouriteArticle get() = articleRepository.favouriteArticle

    fun createArticle(createArticleRequest: CreateArticleRequest){
        viewModelScope.launch {
            articleRepository.createArticle(createArticleRequest)
        }
    }

    fun getArticle(){
        viewModelScope.launch {
            articleRepository.getArticle()
        }
    }

    fun getArticleUsername(username:String){
        viewModelScope.launch {
            articleRepository.getArticleUsername(username)
        }
    }

    fun getFavouriteArticleUsername(username: String){
        viewModelScope.launch {
            articleRepository.getFavouriteArticleByUsername(username)
        }
    }

    fun addFavourite(slug: String){
        viewModelScope.launch {
            articleRepository.addFavourite(slug)
        }
    }

    fun deleteFromFavourite(slug: String){
        viewModelScope.launch {
            articleRepository.deleteFromFavourite(slug)
        }
    }

    fun getProfile(username: String){
        viewModelScope.launch {
            articleRepository.getProfile(username)
        }
    }

    fun validatePage(title : String,description:String,article:String): String {
        var result = ""
        if(title.isEmpty() || description.isEmpty() || article.isEmpty()){
             result = "Enter the credential"
        }
        return result

    }

}