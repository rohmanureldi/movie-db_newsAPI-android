package com.eldirohmanur.movie_app.presentation.review

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.eldirohmanur.movie_app.databinding.BottomSheetReviewBinding
import com.eldirohmanur.movie_app.presentation.moviedetail.MovieDetailViewModel
import com.hi.dhl.binding.viewbind
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject

class ReviewActivity : AppCompatActivity() {
    private val vm: MovieDetailViewModel by inject()
    private val binding: BottomSheetReviewBinding by viewbind()
    private val adapter by lazy { ReviewAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            rvReview.adapter = adapter
            val movieId = intent.getIntExtra("movieId", 0)
            vm.getReviews(movieId)
        }

        lifecycleScope.launchWhenStarted {
            vm.reviews.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}