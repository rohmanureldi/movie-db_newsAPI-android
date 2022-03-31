package com.eldirohmanur.newsapp.presentation.articles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.eldirohmanur.movie_app.presentation.base.openLink
import com.eldirohmanur.newsapp.databinding.ActivityArticlesBinding
import com.hi.dhl.binding.viewbind
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject

class ArticleListActivity: AppCompatActivity() {
    private val binding: ActivityArticlesBinding by viewbind()
    private val adapter by lazy { ArticlesAdapter(openLink()) }
    private val vm by inject<ArticleListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sourceId = intent.getStringExtra("sourceId") ?: ""
        val sourceName = intent.getStringExtra("sourceName") ?: "-"
        with(binding) {
            rvArticles.adapter = adapter
            tvTitle.text = StringBuilder("Articles by: $sourceName")
        }
        vm.getArticleList(sourceId, lifecycleScope)

        lifecycleScope.launchWhenStarted {
            vm.pagingDataFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}