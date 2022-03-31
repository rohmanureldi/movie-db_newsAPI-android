package com.eldirohmanur.movie_app.data.model

import com.eldirohmanur.movie_app.presentation.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponseItem(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("page") val page: Int? = null,
    @SerializedName("results") val results: List<MovieItem>? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("total_results") val totalResults: Int? = null,
    @SerializedName("errors") val errors: List<String>? = null
)

data class MovieItem(
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("adult") val adult: Boolean? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("genre_ids") val genreIds: List<Int>? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("vote_count") val voteCount: Int? = null,
    @SerializedName("video") val video: Boolean? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null

) {
    fun toDomain() = Movie(
        id = id ?: 0,
        posterPath = posterPath.orEmpty(),
        adult = adult ?: false,
        overview = overview.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        genreIds = genreIds.orEmpty(),
        originalTitle = originalTitle.orEmpty(),
        originalLanguage = originalLanguage.orEmpty(),
        title = title.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        popularity = popularity ?: 0.0,
        voteCount = voteCount ?: 0,
        video = video ?: false,
        voteAverage = voteAverage ?: 0.0
    )
}