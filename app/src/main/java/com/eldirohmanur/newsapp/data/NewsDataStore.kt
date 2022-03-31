package com.eldirohmanur.newsapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eldirohmanur.newsapp.data.model.NewsArticlePagingSource
import com.eldirohmanur.newsapp.data.model.NewsArticlePagingSource.Companion.NETWORK_BUFFER_SIZE
import com.eldirohmanur.newsapp.data.remote.NewsAPI
import com.eldirohmanur.newsapp.domain.model.NewsArticle
import com.eldirohmanur.newsapp.domain.model.NewsSource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class NewsDataStore(private val api: NewsAPI): NewsRepository {

    override suspend fun getAllNewsSourcesByCategory(category: String): List<NewsSource> {
//        return try {
            return api.getAllNewsSourcesByCategory(category).getNewsSources()
//        } catch (e: Exception) {
//            emptyList()
//        }
    }

    override suspend fun getAllArticleFromPublisher(sourceId: String?): Flow<PagingData<NewsArticle>> {
        return try {
            Pager(
                config = PagingConfig(pageSize = NETWORK_BUFFER_SIZE, enablePlaceholders = false),
                pagingSourceFactory = { NewsArticlePagingSource(api, sourceId) }
            ).flow
        } catch (e: HttpException) {
            throw e
        }
    }

    override fun getHomeArticles(query: String?): Flow<PagingData<NewsArticle>> {
        return try {
            Pager(
                config = PagingConfig(pageSize = NETWORK_BUFFER_SIZE, enablePlaceholders = false),
                pagingSourceFactory = { NewsArticlePagingSource(api, null, query) }
            ).flow
        } catch (e: Exception) {
            throw e
        }
//        return Pager(
//            config = PagingConfig(pageSize = NETWORK_BUFFER_SIZE, enablePlaceholders = false),
//            pagingSourceFactory = { NewsArticlePagingSource(api, null, query) }
//        ).flow
    }
}