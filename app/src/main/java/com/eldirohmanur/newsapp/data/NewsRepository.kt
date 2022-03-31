package com.eldirohmanur.newsapp.data

import androidx.paging.PagingData
import com.eldirohmanur.newsapp.data.model.NewsArticleItem
import com.eldirohmanur.newsapp.domain.model.NewsArticle
import com.eldirohmanur.newsapp.domain.model.NewsSource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getAllNewsSourcesByCategory(category: String): List<NewsSource>
    suspend fun getAllArticleFromPublisher(sourceId: String?): Flow<PagingData<NewsArticle>>
    fun getHomeArticles(query: String?): Flow<PagingData<NewsArticle>>
}