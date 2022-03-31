package com.eldirohmanur.movie_app.presentation.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eldirohmanur.movie_app.data.MovieRepo
import com.eldirohmanur.movie_app.presentation.domain.model.Movie
import com.eldirohmanur.movie_app.presentation.domain.model.Review
import com.eldirohmanur.movie_app.presentation.domain.model.Trailer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val repo: MovieRepo
): ViewModel() {
    private val _trailers = MutableLiveData<List<Trailer>>()
    val trailers : LiveData<List<Trailer>> = _trailers

    val reviews = MutableSharedFlow<PagingData<Review>>(replay = 1)

    fun getTrailers(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getTrailers(movieId)
            _trailers.postValue(response)
        }
    }

    fun getReviews(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getMovieReview(movieId).cachedIn(viewModelScope)
            reviews.emitAll(response)
        }
    }
}