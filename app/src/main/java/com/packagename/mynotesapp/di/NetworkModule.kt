package com.packagename.mynotesapp.di

import com.packagename.mynotesapp.api.ArticleApi
import com.packagename.mynotesapp.api.AuthInterceptor
import com.packagename.mynotesapp.api.UserApi
import com.packagename.mynotesapp.utilis.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofitBuilder() : Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor) : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build() //here we use okhttpclient to add our authinterceptor in it

    }

    @Singleton
    @Provides
    fun providesUserApi(retrofitBuilder : Retrofit.Builder ): UserApi{
        return retrofitBuilder.build().create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun providesArticleApi(retrofitBuilder: Builder , okHttpClient: OkHttpClient) : ArticleApi{
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(ArticleApi::class.java)

    }




}