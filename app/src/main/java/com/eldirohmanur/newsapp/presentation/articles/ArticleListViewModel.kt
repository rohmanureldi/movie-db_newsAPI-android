package com.eldirohmanur.newsapp.presentation.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eldirohmanur.newsapp.data.NewsRepository
import com.eldirohmanur.newsapp.domain.model.NewsArticle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

class ArticleListViewModel(
    private val repo: NewsRepository
): ViewModel() {

    val pagingDataFlow =  MutableSharedFlow<PagingData<NewsArticle>>(replay = 1)

    init {
        viewModelScope.launch {
            pagingDataFlow.emit(PagingData.empty())
        }
    }

    fun getArticleList(sourceId: String, pagingScope: CoroutineScope) {
        viewModelScope.launch {
            val response = repo.getAllArticleFromPublisher(sourceId).cachedIn(pagingScope)
            pagingDataFlow.emitAll(response)
        }
    }
}