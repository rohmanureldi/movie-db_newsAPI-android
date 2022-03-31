package com.eldirohmanur.movie_app.data.remote

import com.eldirohmanur.movie_app.data.model.MovieGenresResponse
import com.eldirohmanur.movie_app.data.model.MovieResponseItem
import com.eldirohmanur.movie_app.data.model.MovieReviewResponse
import com.eldirohmanur.movie_app.data.model.MovieTrailersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    @GET("genre/movie/list")
    suspend fun getGenreList(): MovieGenresResponse

    @GET("discover/movie")
    suspend fun discoverMovie(
        @Query("with_genres") genres: Int?,
        @Query("page") page: Int
    ): MovieResponseItem

    @GET("movie/{movieId}/reviews")
    suspend fun getMovieReviews(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int,
    ): MovieReviewResponse

    @GET("movie/{movieId}/videos")
    suspend fun getTrailers(
        @Path("movieId") movieId: Int
    ): MovieTrailersResponse
}