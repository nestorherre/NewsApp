package com.example.newsapp.viewmodel

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.data.Article
import com.example.newsapp.model.repository.RepositoryI
import com.example.newsapp.utils.Constants.Companion.GONE
import com.example.newsapp.utils.Constants.Companion.SERVER_ERROR
import com.example.newsapp.utils.Constants.Companion.VISIBLE
import com.example.newsapp.utils.InternetConnection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsHomeViewModel(private val repository: RepositoryI, private val context: Context?, private val hasInternet: Boolean = true): ViewModel(){

    private val _myResponse: MutableLiveData<List<Article>> = MutableLiveData()
    val myResponse: LiveData<List<Article>> = _myResponse
    private val _featuredArticle: MutableLiveData<Article> = MutableLiveData()
    val featuredArticle: LiveData<Article> = _featuredArticle
    private val _loading: MutableLiveData<Int> = MutableLiveData()
    val loading: LiveData<Int> = _loading
    private val _displayHomeViews: MutableLiveData<Int> = MutableLiveData()
    val displayHomeViews: LiveData<Int> = _displayHomeViews
    private val _displayErrorViews: MutableLiveData<Int> = MutableLiveData()
    val displayErrorViews: LiveData<Int> = _displayErrorViews

    fun getNews(delayViews: Long = 1500){
        setObservablesValue(VISIBLE, GONE, GONE)
        if (InternetConnection.isActive(context, hasInternet )){
            viewModelScope.launch {
                val newsList = repository.getNews()
                if (newsList.isSuccessful){
                    _featuredArticle.value = newsList.body()!!.articles.get(0)
                    _myResponse.value = newsList.body()!!.articles.minus(newsList.body()!!.articles.get(0))
                    setObservablesValue(GONE, VISIBLE, GONE)
                }else{
                    viewModelScope.launch {
                        changeViewsVisibility(delayViews, GONE, GONE, SERVER_ERROR)
                    }
                }
            }
        }else{
            viewModelScope.launch {
                changeViewsVisibility(delayViews, GONE, GONE, VISIBLE)
            }
        }
    }

    private suspend fun changeViewsVisibility(delayViews: Long, loading: Int, homeViews: Int, errorViews: Int){
        delay(delayViews)
        setObservablesValue(loading, homeViews, errorViews)
    }

    private fun setObservablesValue(loading: Int, homeViews: Int, errorViews: Int) {
        this._loading.value = loading
        _displayHomeViews.value = homeViews
        _displayErrorViews.value = errorViews
    }

}