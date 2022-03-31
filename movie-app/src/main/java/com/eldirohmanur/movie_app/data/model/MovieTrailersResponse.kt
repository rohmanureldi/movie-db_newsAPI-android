package com.eldirohmanur.movie_app.data.model

import com.eldirohmanur.movie_app.presentation.domain.model.Trailer
import com.google.gson.annotations.SerializedName

data class MovieTrailersResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("results") val results: List<MovieTrailersItem>? = null
)

data class MovieTrailersItem(
    @SerializedName("name") val name: String? = null,
    @SerializedName("key") val key: String? = null,
    @SerializedName("site") val site: String? = null,
    @SerializedName("size") val size: Int? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("official") val official: Boolean? = null,
    @SerializedName("published_at") val publishedAt: String? = null,
    @SerializedName("id") val id: String? = null
) {
    fun toDomain() = Trailer(
        name = name.orEmpty(),
        key = key.orEmpty(),
        site = site.orEmpty(),
        size = size ?: 0,
        type = type.orEmpty(),
        official = official ?: false,
        publishedAt = publishedAt.orEmpty(),
        id = id.orEmpty()
    )
}