package com.example.newsapp.model.data

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)