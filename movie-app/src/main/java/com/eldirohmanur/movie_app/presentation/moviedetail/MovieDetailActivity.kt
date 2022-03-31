package com.eldirohmanur.movie_app.presentation.moviedetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.eldirohmanur.movie_app.databinding.ActivityMovieDetailBinding
import com.eldirohmanur.movie_app.databinding.ItemTrailerLinkBinding
import com.eldirohmanur.movie_app.presentation.base.BaseAdapter
import com.eldirohmanur.movie_app.presentation.base.openLink
import com.eldirohmanur.movie_app.presentation.domain.model.Movie
import com.eldirohmanur.movie_app.presentation.domain.model.Trailer
import com.eldirohmanur.movie_app.presentation.review.ReviewActivity
import com.hi.dhl.binding.viewbind
import org.koin.android.ext.android.inject

class MovieDetailActivity : AppCompatActivity() {
    private val binding: ActivityMovieDetailBinding by viewbind()
    private val vm: MovieDetailViewModel by inject()
    private val trailerAdapter by lazy {
        object : BaseAdapter<Trailer, ItemTrailerLinkBinding>() {
            override fun getBinding(parent: ViewGroup): ItemTrailerLinkBinding {
                return ItemTrailerLinkBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }

            override fun onBind(binding: ItemTrailerLinkBinding, item: Trailer) {
                binding.tvLink.text = item.name
                binding.tvLink.setOnClickListener {
                    openTrailer(item.key)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = intent.getParcelableExtra<Movie>("movie")
        with(binding) {
            rvTrailersLink.adapter = trailerAdapter
            args?.let {
                val posterUrl = "https://image.tmdb.org/t/p/w200${args.posterPath}"
                Glide.with(this@MovieDetailActivity)
                    .load(posterUrl)
                    .into(imgPoster)
                tvTitle.text = args.title
                tvReleaseDate.text = args.releaseDate
                tvSynopsis.text = args.overview
                tvTotalVote.text = StringBuilder("(by ${args.voteCount} Total Vote)")
                tvRating.text = StringBuilder("Rating: ${args.voteAverage}")
                tvSeeReviews.isVisible = args.voteCount > 0
                tvSeeReviews.setOnClickListener { toReviewPage(args.id) }
                vm.getTrailers(args.id)
            }

            vm.trailers.observe(this@MovieDetailActivity) {
                trailerAdapter.replaceAll(it)
            }
        }
    }

    private fun toReviewPage(movieId: Int) {
        val intent = Intent(this, ReviewActivity::class.java).apply {
            putExtra("movieId", movieId)
        }
        startActivity(intent)
    }

    private fun openTrailer(key: String) {
        val link = "https://www.youtube.com/watch?v=$key"
        openLink().invoke(link)
    }
}