package com.eldirohmanur.movie_app.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eldirohmanur.movie_app.data.MovieRepo
import com.eldirohmanur.movie_app.presentation.domain.model.Movie
import com.eldirohmanur.movie_app.presentation.domain.model.MovieGenre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

class MovieViewModel(
    private val repo: MovieRepo
) : ViewModel() {
    private val _genres = MutableLiveData<List<MovieGenre>>(emptyList())
    val genres: LiveData<List<MovieGenre>> = _genres

    private val _selectedGenre = MutableLiveData<MovieGenre?>(null)
    val selectedGenre: LiveData<MovieGenre?> = _selectedGenre

    private val _movies = MutableSharedFlow<PagingData<Movie>>(replay = 1)
    val movies = _movies.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.getGenreList()
            _genres.postValue(response)
        }
        discoverMovies()
    }

    private fun discoverMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.discoverMovies(selectedGenre.value?.id).cachedIn(this)
            _movies.emitAll(response)
        }
    }

    fun updateSelectedGenre(genre: MovieGenre) {
        val currentGenre = _selectedGenre.value
        if (currentGenre?.id == genre.id) {
            _selectedGenre.postValue(null)
        } else {
            _selectedGenre.postValue(genre)
        }
        discoverMovies()
    }
}