package com.eldirohmanur.newsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eldirohmanur.newsapp.App
import com.eldirohmanur.newsapp.data.NewsRepository
import com.eldirohmanur.newsapp.domain.model.NewsArticle
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    private val repo: NewsRepository
): ViewModel() {
    val pagingDataFlow =  MutableSharedFlow<PagingData<NewsArticle>>(replay = 1)
    private val _uiAction = MutableStateFlow<List<MainActivityUiEvent>>(emptyList())
    val uiAction = _uiAction.asStateFlow()

    var query: String? = "a"
    set(value) {
        field = value?.trim()?.ifEmpty { "a" }
        addEvent(MainActivityUiEvent.ScrollToTop)
        searchArticle()
    }

    init {
        searchArticle()
    }

    private fun addEvent(event: MainActivityUiEvent) {
        _uiAction.update {
            it + event
        }
    }

    fun eventHandled(eventId: String) {
        _uiAction.update {
            it.filterNot {
                it.id == eventId
            }
        }
    }

    fun requestScroll() {
        addEvent(MainActivityUiEvent.ScrollToTop)
    }

    fun hideEmptyText() {
        addEvent(MainActivityUiEvent.HideEmptyText)
    }

    fun showEmptyText() {
        addEvent(MainActivityUiEvent.ShowEmptyList)
    }

    fun showError() {
        addEvent(MainActivityUiEvent.ShowLimitedAPIReached)
    }

    private fun searchArticle() {
        viewModelScope.launch(App.globalCoroutineExceptionHandler) {
//            try {
                val response = repo.getHomeArticles(query).cachedIn(this)
                pagingDataFlow.emitAll(response)
//            } catch (e: Exception) {
//                addEvent(MainActivityUiEvent.ShowLimitedAPIReached)
//            }
        }
    }
}

sealed class MainActivityUiEvent {
    val id: String = UUID.randomUUID().toString()

    object ShowEmptyList: MainActivityUiEvent()
    object ShowLimitedAPIReached: MainActivityUiEvent()
    object HideEmptyText: MainActivityUiEvent()
    object ScrollToTop: MainActivityUiEvent()
}