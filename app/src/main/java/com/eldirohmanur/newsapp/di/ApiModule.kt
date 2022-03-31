package com.eldirohmanur.newsapp.di

import com.eldirohmanur.movie_app.data.MovieDataStore
import com.eldirohmanur.movie_app.data.MovieRepo
import com.eldirohmanur.movie_app.data.remote.MovieAPI
import com.eldirohmanur.movie_app.presentation.MovieViewModel
import com.eldirohmanur.movie_app.presentation.moviedetail.MovieDetailViewModel
import com.eldirohmanur.newsapp.data.NewsDataStore
import com.eldirohmanur.newsapp.data.NewsRepository
import com.eldirohmanur.newsapp.data.remote.NewsAPI
import com.eldirohmanur.newsapp.di.NetworkModule.createOkHttpClient
import com.eldirohmanur.newsapp.presentation.MainViewModel
import com.eldirohmanur.newsapp.presentation.articles.ArticleListViewModel
import com.eldirohmanur.newsapp.presentation.sourcelist.NewsSourcesViewModel
import org.koin.dsl.module

val apiModule = module {
    single { createOkHttpClient(get()) }
    single<NewsAPI> { NetworkModule.createApi("", get(), "https://newsapi.org/v2/") }
    single<MovieAPI> { NetworkModule.createApi("", get(), "https://api.themoviedb.org/3/") }

    single<NewsRepository> { NewsDataStore(get()) }
    single<MovieRepo> { MovieDataStore(get()) }

    single { MainViewModel(get()) }
    single { NewsSourcesViewModel(get()) }
    single { ArticleListViewModel(get()) }


    single { MovieViewModel(get()) }
    single { MovieDetailViewModel(get()) }
}
