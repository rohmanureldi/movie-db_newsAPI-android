package com.eldirohmanur.newsapp.presentation.sourcelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eldirohmanur.newsapp.App.Companion.globalCoroutineExceptionHandler
import com.eldirohmanur.newsapp.data.NewsRepository
import com.eldirohmanur.newsapp.domain.model.NewsSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsSourcesViewModel(private val repo: NewsRepository) : ViewModel() {
    private val _newsSources = MutableLiveData<List<NewsSource>>(emptyList())
    val newsSources: LiveData<List<NewsSource>> = _newsSources

    fun getNewsSources(category: String) {
        viewModelScope.launch(globalCoroutineExceptionHandler + Dispatchers.IO) {
            val newsSources = repo.getAllNewsSourcesByCategory(category)
            _newsSources.value = newsSources
        }
    }
}