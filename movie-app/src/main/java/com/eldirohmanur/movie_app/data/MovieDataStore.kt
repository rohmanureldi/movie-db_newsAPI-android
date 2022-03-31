package com.eldirohmanur.movie_app.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.eldirohmanur.movie_app.data.model.MovieReviewResponse
import com.eldirohmanur.movie_app.data.remote.MovieAPI
import com.eldirohmanur.movie_app.presentation.domain.model.Movie
import com.eldirohmanur.movie_app.presentation.domain.model.MovieGenre
import com.eldirohmanur.movie_app.presentation.domain.model.Review
import com.eldirohmanur.movie_app.presentation.domain.model.Trailer
import kotlinx.coroutines.flow.Flow

class MovieDataStore(private val api: MovieAPI) : MovieRepo {
    override suspend fun getGenreList(): List<MovieGenre> {
        return api.getGenreList().genres?.map { it.toDomain() }.orEmpty()
    }

    override suspend fun discoverMovies(genres: Int?): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MoviePagingSource(api, genres) }
        ).flow
    }

    override suspend fun getMovieReview(movieId: Int): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(pageSize = 6),
            pagingSourceFactory = { ReviewPagingSource(api, movieId) }
        ).flow
    }

    override suspend fun getTrailers(movieId: Int): List<Trailer> {
        return api.getTrailers(movieId).results?.map { it.toDomain() }.orEmpty()
    }
}