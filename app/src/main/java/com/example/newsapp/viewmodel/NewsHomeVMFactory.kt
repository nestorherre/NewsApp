package com.example.newsapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.model.repository.RepositoryI

class NewsHomeVMFactory(private val repository: RepositoryI, private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsHomeViewModel(repository, context) as T
    }
}