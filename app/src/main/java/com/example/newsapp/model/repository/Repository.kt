package com.example.newsapp.model.repository

import com.example.newsapp.model.api.RetrofitInstance
import com.example.newsapp.model.data.News
import retrofit2.Response

class Repository: RepositoryI {

    override suspend fun getNews(): Response<News> {
        return RetrofitInstance.api.getNews()
    }
}