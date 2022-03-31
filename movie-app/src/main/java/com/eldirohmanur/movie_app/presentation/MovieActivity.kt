package com.eldirohmanur.movie_app.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.eldirohmanur.movie_app.databinding.ActivityMovieBinding
import com.eldirohmanur.movie_app.databinding.ItemGenreBinding
import com.eldirohmanur.movie_app.presentation.base.BaseAdapter
import com.eldirohmanur.movie_app.presentation.domain.model.Movie
import com.eldirohmanur.movie_app.presentation.domain.model.MovieGenre
import com.eldirohmanur.movie_app.presentation.moviedetail.MovieDetailActivity
import com.hi.dhl.binding.viewbind
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieActivity : AppCompatActivity() {
    private val binding: ActivityMovieBinding by viewbind()
    private val vm: MovieViewModel by viewModel()
    private val genreAdapter by lazy {
        object : BaseAdapter<MovieGenre, ItemGenreBinding>() {
            override fun getBinding(parent: ViewGroup): ItemGenreBinding {
                return ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            }

            override fun onBind(binding: ItemGenreBinding, item: MovieGenre) {
                with(binding) {
                    btnGenre.text = item.name
                    btnGenre.setOnClickListener {
                        onGenreClicked(item)
                    }
                }
            }
        }
    }

    private val movieAdapter by lazy { MovieAdapter(this@MovieActivity::onMovieClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            rvGenre.adapter = genreAdapter
            rvMovie.adapter = movieAdapter

            lifecycleScope.launchWhenStarted {
                vm.movies.collectLatest {
                    movieAdapter.submitData(it)
                }
            }

            vm.genres.observe(this@MovieActivity) {
                genreAdapter.replaceAll(it)
            }

            vm.selectedGenre.observe(this@MovieActivity) {
                binding.tvDiscoverMovie.text = if (it == null) {
                    StringBuilder("Discover Movies")
                } else {
                    StringBuilder("Discover Movies (Category: ${it.name})")
                }
            }
        }
    }

    private fun onMovieClicked(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra("movie", movie)
        }
        startActivity(intent)
    }

    private fun onGenreClicked(genre: MovieGenre) {
        vm.updateSelectedGenre(genre)
    }
}