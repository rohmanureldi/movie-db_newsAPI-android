package com.eldirohmanur.newsapp.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat

fun EditText.onTextChangedWithDebounce(
    scope: CoroutineScope,
    delayMs: Long = 500L,
    destinationFunction: (String) -> Unit
) {
    var debounceJob: Job? = null
    doOnTextChanged { text, _, _, _ ->
        debounceJob?.cancel()
        debounceJob = scope.launch {
            delay(delayMs)
            destinationFunction(text.toString())
        }
    }
}
