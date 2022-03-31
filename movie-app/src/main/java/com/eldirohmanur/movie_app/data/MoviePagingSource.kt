package com.eldirohmanur.movie_app.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eldirohmanur.movie_app.data.remote.MovieAPI
import com.eldirohmanur.movie_app.presentation.domain.model.Movie

class MoviePagingSource(
    private val api: MovieAPI,
    private val genres: Int? = null
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val nextPageNumber = params.key ?: 1
        return try {
            val response = api.discoverMovie(genres, nextPageNumber)
            val nextKey = if (!response.errors.isNullOrEmpty()) {
                null
            } else {
                response.page?.plus(1)
            }
            LoadResult.Page(
                data = response.results?.map { it.toDomain() }.orEmpty(),
                prevKey = response.page?.takeIf { it > 1 },
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}