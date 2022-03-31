package com.eldirohmanur.newsapp.presentation.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eldirohmanur.newsapp.databinding.ItemNewsArticleBinding
import com.eldirohmanur.newsapp.domain.model.NewsArticle

class ArticlesAdapter(
    private val onArticleClick: (String) -> Unit
)
    : PagingDataAdapter<NewsArticle, ArticlesAdapter.NewsSourceViewHolder>(
    ARTICLE_COMPARATOR
) {

    override fun onBindViewHolder(holder: NewsSourceViewHolder, position: Int) {
        with(holder.binding) {
            val item = getItem(position)
            item?.let {
                tvTitle.text = item.title
                tvContent.text = item.content
                tvDate.text = item.publishedAt
                Glide.with(imgArticle)
                    .load(item.urlToImage)
                    .centerCrop()
                    .into(imgArticle)
                containerArticle.setOnClickListener {
                    onArticleClick(item.url)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSourceViewHolder {
        val binding = ItemNewsArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsSourceViewHolder(binding)
    }

    inner class NewsSourceViewHolder(internal val binding: ItemNewsArticleBinding): RecyclerView.ViewHolder(binding.root)

    companion object {
        private val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<NewsArticle>() {
            override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean =
                oldItem.publishedAt == newItem.publishedAt

            override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean =
                oldItem == newItem
        }
    }
}

