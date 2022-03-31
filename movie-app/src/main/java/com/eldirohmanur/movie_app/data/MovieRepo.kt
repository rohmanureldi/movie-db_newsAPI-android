package com.eldirohmanur.movie_app.data

import androidx.paging.PagingData
import com.eldirohmanur.movie_app.data.model.MovieReviewResponse
import com.eldirohmanur.movie_app.presentation.domain.model.Movie
import com.eldirohmanur.movie_app.presentation.domain.model.MovieGenre
import com.eldirohmanur.movie_app.presentation.domain.model.Review
import com.eldirohmanur.movie_app.presentation.domain.model.Trailer
import kotlinx.coroutines.flow.Flow

interface MovieRepo {
    suspend fun getGenreList(): List<MovieGenre>
    suspend fun discoverMovies(genres: Int?): Flow<PagingData<Movie>>
    suspend fun getMovieReview(movieId: Int): Flow<PagingData<Review>>

    suspend fun getTrailers(
        movieId: Int
    ): List<Trailer>
}