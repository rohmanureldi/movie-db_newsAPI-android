package com.eldirohmanur.movie_app.data.model

import com.eldirohmanur.movie_app.presentation.domain.model.MovieGenre
import com.google.gson.annotations.SerializedName

data class MovieGenresResponse(
    @SerializedName("genres") val genres: List<MovieGenreItem>? = null
)

data class MovieGenreItem(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null
) {
    fun toDomain() = MovieGenre(
        id = id ?: 0,
        name = name.orEmpty()
    )
}