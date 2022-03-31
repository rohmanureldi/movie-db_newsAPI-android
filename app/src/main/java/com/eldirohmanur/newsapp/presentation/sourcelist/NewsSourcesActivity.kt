package com.eldirohmanur.newsapp.presentation.sourcelist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.eldirohmanur.movie_app.presentation.base.BaseAdapter
import com.eldirohmanur.newsapp.databinding.ActivityNewsSourcesBinding
import com.eldirohmanur.newsapp.databinding.ItemNewsSourceBinding
import com.eldirohmanur.newsapp.domain.model.NewsSource
import com.eldirohmanur.newsapp.presentation.articles.ArticleListActivity
import com.hi.dhl.binding.viewbind
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class NewsSourcesActivity: AppCompatActivity() {
    private val binding: ActivityNewsSourcesBinding by viewbind()
    private val vm by viewModel<NewsSourcesViewModel>()
    private val adapter by lazy {
        object: BaseAdapter<NewsSource, ItemNewsSourceBinding>() {
            override fun getBinding(parent: ViewGroup): ItemNewsSourceBinding {
                return ItemNewsSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            }

            override fun onBind(binding: ItemNewsSourceBinding, item: NewsSource) {
                with(binding) {
                    tvNewsSource.text = item.name
                    tvNewsSource.setOnClickListener {
                        goToArticleActivity(item.id, item.name)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.rvSources.adapter = adapter
        val category = intent.getStringExtra("category")
        category?.let {
            binding.tvTitle.text = StringBuilder("Showing result of sources by category of: ${it.uppercase(
                Locale.getDefault()
            )}")
            vm.getNewsSources(it)
        }

        vm.newsSources.observe(this) {
            adapter.replaceAll(it)
        }
    }

    fun goToArticleActivity(sourceId: String, sourceName: String) {
        val intent = Intent(this, ArticleListActivity::class.java).apply {
            putExtra("sourceId", sourceId)
            putExtra("sourceName", sourceName)
        }
        startActivity(intent)
    }
}