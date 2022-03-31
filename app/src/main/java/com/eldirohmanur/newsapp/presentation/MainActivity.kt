package com.eldirohmanur.newsapp.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.eldirohmanur.newsapp.R
import com.eldirohmanur.movie_app.presentation.base.BaseAdapter
import com.eldirohmanur.movie_app.presentation.base.openLink
import com.eldirohmanur.newsapp.base.onTextChangedWithDebounce
import com.eldirohmanur.newsapp.databinding.ActivityMainBinding
import com.eldirohmanur.newsapp.databinding.ItemHeadlineCategoriesBinding
import com.eldirohmanur.newsapp.presentation.articles.ArticlesAdapter
import com.eldirohmanur.newsapp.presentation.sourcelist.NewsSourcesActivity
import com.hi.dhl.binding.viewbind
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewbind()
    private val vm: MainViewModel by viewModel()
    private val categoryAdapter by lazy {
        object : BaseAdapter<String, ItemHeadlineCategoriesBinding>() {
            override fun getBinding(parent: ViewGroup): ItemHeadlineCategoriesBinding {
                return ItemHeadlineCategoriesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }

            override fun onBind(binding: ItemHeadlineCategoriesBinding, item: String) {
                with(binding) {
                    tvCategory.text = item
                    tvCategory.setOnClickListener {
                        goToNewsSources(item)
                    }
                }
            }
        }
    }
    private val articleAdapter by lazy { ArticlesAdapter(openLink()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            val categories = resources.getStringArray(R.array.categories)
            rvCategories.adapter = categoryAdapter
            categoryAdapter.replaceAll(categories.toList())
            rvArticles.adapter = articleAdapter

            etSearch.onTextChangedWithDebounce(lifecycleScope) {
                vm.query = it
            }

            lifecycleScope.launch {
                articleAdapter.loadStateFlow.collectLatest {
                    when {
                        it.refresh is LoadState.NotLoading && it.append.endOfPaginationReached -> {
                            if (articleAdapter.itemCount <= 0) {
                                vm.showEmptyText()
                            } else {
                                vm.hideEmptyText()
                            }
                        }
                        it.refresh is LoadState.Error -> {
                            vm.showError()
                        }
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                vm.pagingDataFlow.collectLatest {
                    articleAdapter.submitData(it)
                    if (articleAdapter.itemCount != 0) {
                        vm.requestScroll()
                        vm.hideEmptyText()
                    } else {
                        vm.showEmptyText()
                    }
                }
            }

            lifecycleScope.launchWhenStarted {
                vm.uiAction.collect {
                    it.firstOrNull()?.let {
                        when (it) {
                            MainActivityUiEvent.ScrollToTop -> {
                                rvArticles.postDelayed({
                                    rvArticles.smoothScrollToPosition(0)
                                }, 200)
                            }
                            MainActivityUiEvent.ShowEmptyList -> {
                                rvArticles.visibility = View.GONE
                                tvEmptyArticle.visibility = View.VISIBLE
                            }
                            MainActivityUiEvent.HideEmptyText -> {
                                rvArticles.visibility = View.VISIBLE
                                tvEmptyArticle.visibility = View.GONE
                            }
                            MainActivityUiEvent.ShowLimitedAPIReached -> {
                                Toast.makeText(
                                    this@MainActivity,
                                    "API limit reached",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        vm.eventHandled(it.id)
                    }
                }
            }
        }
    }

    private fun goToNewsSources(category: String) {
        val intent = Intent(this, NewsSourcesActivity::class.java).apply {
            putExtra("category", category)
        }
        startActivity(intent)
    }
}