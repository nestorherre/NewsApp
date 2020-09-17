package com.example.newsapp.model.repository

import com.example.newsapp.model.data.News
import retrofit2.Response

interface RepositoryI {

    suspend fun getNews(): Response<News>
}