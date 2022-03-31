package com.eldirohmanur.newsapp.data.remote

import com.eldirohmanur.newsapp.data.model.NewsArticleItem
import com.eldirohmanur.newsapp.data.model.NewsSourceItem
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("top-headlines/sources")
    suspend fun getAllNewsSourcesByCategory(
        @Query("category") category: String
    ): NewsSourceItem

    @GET("everything")
    suspend fun getArticleBySpecificPublisher(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 10,
        @Query("sources") sourceId: String
    ): NewsArticleItem

    @GET("everything")
    suspend fun getHomeArticles(
        @Query("q") query: String?,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 10
    ): NewsArticleItem
}