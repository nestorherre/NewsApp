package com.example.newsapp.model.repository

import com.example.newsapp.model.data.Article
import com.example.newsapp.model.data.News
import com.example.newsapp.model.data.Source
import retrofit2.Response

class RepositorySuccessTest(): RepositoryI {

    val source = Source("usa-today", "USA Today")
    val article1 = Article(
        "Joe Jacquez, John Bacon",
        "The storms impacts will be felt across the southern U.S. for days after its initial landfall.\r\nAccuweather\r\nPENSACOLA, Fla. Hurricane Sally made landfall early Wednesday along Alabama's Gulf Coast as… [+4495 chars]",
        "Hurricane Sally made landfall early Wednesday as a Category 2 storm near Gulf Shores, Alabama, the National Hurricane Center said.",
        "2020-09-16T12:01:24Z",
        source,
        "Category 2 Hurricane Sally makes landfall in Alabama amid warnings of 'historic, catastrophic' flooding - USA TODAY",
        "https://www.usatoday.com/story/news/nation/2020/09/16/hurricane-sally-makes-landfall-near-gulf-shores-alabama/5814231002/",
        "https://www.gannett-cdn.com/presto/2020/09/15/USAT/e321cfa1-82a9-41f2-9cee-c9a028462f8c-AP_Tropical_Weather.jpg?crop=5486,3086,x0,y278&width=3200&height=1680&fit=bounds"
    )
    val article2 = Article(
        "Myself",
        "Test Content",
        "Test Desc",
        "2020-09-16T12:01:24Z",
        source,
        "Test title",
        "test url",
        "test img"
    )
    val articles = listOf<Article>(article1,article2)
    val newsTwoArticles = News(articles, "ok", 1)

    override suspend fun getNews(): Response<News> {
        return Response.success(newsTwoArticles)
    }
}