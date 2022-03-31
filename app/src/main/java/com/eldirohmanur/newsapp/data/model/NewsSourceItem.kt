package com.eldirohmanur.newsapp.data.model

import com.eldirohmanur.newsapp.domain.model.NewsSource
import com.google.gson.annotations.SerializedName

data class NewsSourceItem(
    @SerializedName("status") val status: String? = null,
    @SerializedName("sources") val sources: ArrayList<SourcesItem>? = null
) {
    fun getNewsSources() = sources?.map { it.toNewsSource() }.orEmpty()
}

data class SourcesItem(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("category") val category: String? = null,
    @SerializedName("language") val language: String? = null,
    @SerializedName("country") val country: String? = null
) {
    fun toNewsSource() = NewsSource(
        id = id.orEmpty(),
        name = name.orEmpty(),
        description = description.orEmpty(),
        url = url.orEmpty(),
        category = category.orEmpty(),
        language = language.orEmpty(),
        country = country.orEmpty()
    )
}
