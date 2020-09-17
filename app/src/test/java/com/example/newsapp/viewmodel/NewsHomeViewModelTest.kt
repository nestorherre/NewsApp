package com.example.newsapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsapp.model.data.Article
import com.example.newsapp.model.data.News
import com.example.newsapp.model.data.Source
import com.example.newsapp.model.repository.RepositoryFailedTest
import com.example.newsapp.model.repository.RepositorySuccessTest
import com.example.newsapp.utils.Constants.Companion.GONE
import com.example.newsapp.utils.Constants.Companion.SERVER_ERROR
import com.example.newsapp.utils.Constants.Companion.VISIBLE
import com.example.newsapp.utils.MainCoroutineRule
import com.example.newsapp.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response


@ExperimentalCoroutinesApi
class NewsHomeViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModelSuccess: NewsHomeViewModel
    lateinit var viewModelFailed: NewsHomeViewModel
    lateinit var viewModelNoInternet: NewsHomeViewModel

    @Before
    fun setup(){
        val repositoryFailed = RepositoryFailedTest()
        val repositorySuccess = RepositorySuccessTest()
        viewModelNoInternet = NewsHomeViewModel(repositorySuccess, null, false)
        viewModelFailed = NewsHomeViewModel(repositoryFailed, null, true)
        viewModelSuccess = NewsHomeViewModel(repositorySuccess, null, true)
    }

    @Test
    fun `if no Internet Connection is detected, errorViews is visible`(){
        viewModelNoInternet.getNews(0)
        val observable = viewModelNoInternet.displayErrorViews.getOrAwaitValue()
        Assert.assertEquals(VISIBLE, observable)
    }

    @Test
    fun `if no Internet Connection is detected, loading is gone`(){
        viewModelNoInternet.getNews(0)
        val observable = viewModelNoInternet.loading.getOrAwaitValue()
        Assert.assertEquals(GONE, observable)

    }

    @Test
    fun `if no Internet Connection is detected, homeViews is gone`(){
        viewModelNoInternet.getNews(0)
        val observable = viewModelNoInternet.displayHomeViews.getOrAwaitValue()
        Assert.assertEquals(GONE, observable)

    }

    @Test
    fun `if I have Internet Connection and API response is successful, homeViews is visible`(){
        viewModelSuccess.getNews(0)
        val observable = viewModelSuccess.displayHomeViews.getOrAwaitValue()
        Assert.assertEquals(VISIBLE, observable)

    }

    @Test
    fun `if I have Internet Connection and API response is successful, loading is gone`(){
        viewModelSuccess.getNews(0)
        val observable = viewModelSuccess.loading.getOrAwaitValue()
        Assert.assertEquals(GONE, observable)
    }

    @Test
    fun `if I have Internet Connection and API response is successful, errorViews is gone`(){
        viewModelSuccess.getNews(0)
        val observable = viewModelSuccess.displayErrorViews.getOrAwaitValue()
        Assert.assertEquals(GONE, observable)
    }

    @Test
    fun `if I have Internet Connection and API response is successful, check featured article value`(){
        // Check dummy values in RepositorySuccessTest
        val testArticle =  Article(
            "Joe Jacquez, John Bacon",
            "The storms impacts will be felt across the southern U.S. for days after its initial landfall.\r\nAccuweather\r\nPENSACOLA, Fla. Hurricane Sally made landfall early Wednesday along Alabama's Gulf Coast as… [+4495 chars]",
            "Hurricane Sally made landfall early Wednesday as a Category 2 storm near Gulf Shores, Alabama, the National Hurricane Center said.",
            "2020-09-16T12:01:24Z",
            Source("usa-today", "USA Today"),
            "Category 2 Hurricane Sally makes landfall in Alabama amid warnings of 'historic, catastrophic' flooding - USA TODAY",
            "https://www.usatoday.com/story/news/nation/2020/09/16/hurricane-sally-makes-landfall-near-gulf-shores-alabama/5814231002/",
            "https://www.gannett-cdn.com/presto/2020/09/15/USAT/e321cfa1-82a9-41f2-9cee-c9a028462f8c-AP_Tropical_Weather.jpg?crop=5486,3086,x0,y278&width=3200&height=1680&fit=bounds"
        )
        viewModelSuccess.getNews(0)
        val observable = viewModelSuccess.featuredArticle.getOrAwaitValue()
        Assert.assertEquals(testArticle, observable)
    }

    @Test
    fun `if I have Internet Connection and API response is successful, check second article value`(){
        // Check dummy values in RepositorySuccessTest
        val testArticle = Article(
            "Myself",
            "Test Content",
            "Test Desc",
            "2020-09-16T12:01:24Z",
            Source("usa-today", "USA Today"),
            "Test title",
            "test url",
            "test img"
        )
        viewModelSuccess.getNews(0)
        val observable = viewModelSuccess.myResponse.getOrAwaitValue()
        Assert.assertEquals(testArticle, observable[0])
    }

    @Test
    fun `if I have Internet Connection and API response fails, errorViews is server error`(){
        viewModelFailed.getNews(0)
        val observable = viewModelFailed.displayErrorViews.getOrAwaitValue()
        Assert.assertEquals(SERVER_ERROR, observable)

    }

    @Test
    fun `if I have Internet Connection and API response fails, loading is gone`(){
        viewModelFailed.getNews(0)
        val observable = viewModelFailed.loading.getOrAwaitValue()
        Assert.assertEquals(GONE, observable)

    }

    @Test
    fun `if I have Internet Connection and API response fails, homeViews is gone`(){
        viewModelFailed.getNews(0)
        val observable = viewModelFailed.displayHomeViews.getOrAwaitValue()
        Assert.assertEquals(GONE, observable)

    }

}