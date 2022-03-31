package com.eldirohmanur.newsapp

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.eldirohmanur.newsapp.di.apiModule
import kotlinx.coroutines.CoroutineExceptionHandler
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    companion object {
        const val USER_TOKEN = "userToken"
        private var instance: App? = null

        fun appContext(): Context {
            return instance!!.applicationContext
        }

        val globalCoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
            Toast.makeText(
                appContext(),
                "API limit reached",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                apiModule
            )
        }
    }
}