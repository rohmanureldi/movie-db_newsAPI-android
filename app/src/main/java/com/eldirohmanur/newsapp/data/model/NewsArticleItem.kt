package com.eldirohmanur.newsapp.data.model

import com.eldirohmanur.movie_app.presentation.base.getReadableDate
import com.eldirohmanur.newsapp.domain.model.ArticleSource
import com.eldirohmanur.newsapp.domain.model.NewsArticle
import com.google.gson.annotations.SerializedName

data class NewsArticleItem(
    @SerializedName("status") val status: String? = null,
    @SerializedName("totalResults") val totalResults: Int? = null,
    @SerializedName("articles") val articles: List<ArticlesItem>? = null
) {
    fun getNewsArticle() = articles?.map { it.toNewsArticle() }.orEmpty()
}

data class ArticlesItem(
    @SerializedName("source") val source: ArticleSourceItem? = null,
    @SerializedName("author") val author: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("urlToImage") val urlToImage: String? = null,
    @SerializedName("publishedAt") val publishedAt: String? = null,
    @SerializedName("content") val content: String? = null
) {
    fun toNewsArticle() = NewsArticle(
        source = source?.toArticleSource() ?: ArticleSource("", ""),
        author = author.orEmpty(),
        title = title.orEmpty(),
        description = description.orEmpty(),
        url = url.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        publishedAt = publishedAt?.getReadableDate().orEmpty(),
        content = content.orEmpty()
    )
}

data class ArticleSourceItem(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null
) {
    fun toArticleSource() = ArticleSource(
        id = id.orEmpty(),
        name = name.orEmpty()
    )
}