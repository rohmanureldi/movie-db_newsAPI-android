package com.eldirohmanur.newsapp.data.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eldirohmanur.newsapp.data.remote.NewsAPI
import com.eldirohmanur.newsapp.domain.model.NewsArticle

class NewsArticlePagingSource(
  private val api: NewsAPI,
  private val sourceId: String?,
  private val query: String? = null
) : PagingSource<Int, NewsArticle>() {
    override fun getRefreshKey(state: PagingState<Int, NewsArticle>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsArticle> {
        val nextPageNumber = params.key ?: 1
        return try {
            val response = if (sourceId != null) {
                api.getArticleBySpecificPublisher(nextPageNumber, params.loadSize, sourceId).getNewsArticle()
            } else {
                api.getHomeArticles(query, nextPageNumber,params.loadSize).getNewsArticle()
            }
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                nextPageNumber + (params.loadSize / NETWORK_BUFFER_SIZE)
            }
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val NETWORK_BUFFER_SIZE = 10
    }
}