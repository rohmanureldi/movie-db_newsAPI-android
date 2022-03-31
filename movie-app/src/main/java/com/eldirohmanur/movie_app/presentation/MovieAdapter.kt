package com.eldirohmanur.movie_app.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eldirohmanur.movie_app.databinding.ItemMovieBinding
import com.eldirohmanur.movie_app.presentation.domain.model.Movie

class MovieAdapter(
    private val onMovieClicked: (Movie) -> Unit
) : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(
    MOVIE_COMPARATOR
) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder.binding) {
            val item = getItem(position)
            item?.let {
                tvTitle.text = item.title
                tvSynopsis.text = item.overview
                tvReleaseDate.text = item.releaseDate
                val posterPath = "https://image.tmdb.org/t/p/w200${item.posterPath}"
                Glide.with(imgPoster)
                    .load(posterPath)
                    .centerCrop()
                    .into(imgPoster)
                tvRating.text = StringBuilder("Rating: ${item.voteAverage}")
                containerMovie.setOnClickListener {
                    onMovieClicked(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    inner class MovieViewHolder(internal val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root)

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id
        }
    }
}