package com.eldirohmanur.newsapp.domain.model

data class NewsArticle(
    val source: ArticleSource,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)

data class ArticleSource(
    val id: String,
    val name: String
)
