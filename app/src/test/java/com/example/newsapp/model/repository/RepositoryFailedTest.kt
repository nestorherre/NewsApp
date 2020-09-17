package com.example.newsapp.model.repository

import com.example.newsapp.model.data.Article
import com.example.newsapp.model.data.News
import com.example.newsapp.model.data.Source
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response

class RepositoryFailedTest(): RepositoryI {

    override suspend fun getNews(): Response<News> {
        return Response.error(500, ResponseBody.create(MediaType.parse("text/plain"), "test"))
    }
}