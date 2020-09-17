package com.example.newsapp.model.api

import com.example.newsapp.model.data.News
import com.example.newsapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET

interface NewsAPI {

    @GET("top-headlines?country=us&apiKey="+API_KEY)
    suspend fun getNews(): Response<News>

}