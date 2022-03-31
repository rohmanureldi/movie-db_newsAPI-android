package com.eldirohmanur.movie_app.presentation.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eldirohmanur.movie_app.databinding.ItemReviewBinding
import com.eldirohmanur.movie_app.presentation.domain.model.Review

class ReviewAdapter : PagingDataAdapter<Review, ReviewAdapter.ReviewViewHolder>(
    REVIEW_COMPARATOR
) {

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        with(holder.binding) {
            val item = getItem(position)
            item?.let {
                tvAuthorName.text = item.authorDetails.name
                tvReviewContent.text = item.content
                tvReviewDate.text = item.updatedAt
                val avatarPath = "https://image.tmdb.org/t/p/w200${item.authorDetails.avatarPath}"
                Glide.with(imgAvatar)
                    .load(avatarPath)
                    .circleCrop()
                    .into(imgAvatar)
                tvRating.text = StringBuilder("Rating: ${item.authorDetails.rating}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    inner class ReviewViewHolder(internal val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val REVIEW_COMPARATOR = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean =
                oldItem.id == newItem.id
        }
    }
}