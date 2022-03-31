package com.eldirohmanur.newsapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.eldirohmanur.newsapp.App.Companion.USER_TOKEN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AppInterceptor(
    private val pref: DataStore<Preferences>
    ): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val tokenKey = stringPreferencesKey(USER_TOKEN)
        val tokenFlow: Flow<String?> = pref.data.map {
            it[tokenKey]
        }
        val token: String = runBlocking {
            tokenFlow.first() ?: "cadae10bc8ca477abb4e78ea07755b56"
        }

        val movieDbToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNWFiZjE4OGMzNmRhNWYyYTdiZmU3N2I1YjkwZjVlNiIsInN1YiI6IjVmZWQ4ZmFlOWEzNThkMDAzZjI1MGIxYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EzncmUUIFw4cePeojImn-qerb-ucD-jwRum1nGWzxpE"

        val usedToken = if (chain.request().url.encodedPath.contains("news")) {
            token
        } else {
            movieDbToken
        }

        val request: Request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $usedToken")
            .build()

        return chain.proceed(request)
    }
}