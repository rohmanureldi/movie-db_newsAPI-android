package com.eldirohmanur.movie_app.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eldirohmanur.movie_app.data.remote.MovieAPI
import com.eldirohmanur.movie_app.presentation.domain.model.Review

class ReviewPagingSource(
    private val api: MovieAPI,
    private val movieId: Int
) : PagingSource<Int, Review>() {
    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        val nextPageNumber = params.key ?: 1
        return try {
            val response = api.getMovieReviews(movieId, nextPageNumber)
            val nextKey =
                if (!response.errors.isNullOrEmpty() || response.results?.isEmpty() == true) {
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