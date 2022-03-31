package com.eldirohmanur.movie_app.presentation.base

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import java.text.ParseException
import java.text.SimpleDateFormat

fun AppCompatActivity.openLink(): (String) -> Unit = {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
    startActivity(intent)
}

@SuppressLint("SimpleDateFormat")
fun String.getReadableDate(): String {
    val formatPattern = "dd-MM-yyyy"
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter = SimpleDateFormat(formatPattern)
    try {
        val result = formatter.format(parser.parse(this))
        return result
    } catch (e: ParseException) {
        print(e)
        val parser = SimpleDateFormat("yyyy-MM-dd'T'00:00'Z'")
        val formatter = SimpleDateFormat(formatPattern)

        return try {
            val result = formatter.format(parser.parse(this))
            result
        } catch (e: Exception) {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val formatter = SimpleDateFormat(formatPattern)

            return try {
                val result = formatter.format(parser.parse(this))
                result
            } catch (e: Exception) {
                this
            }
        }
    } catch (e: Exception) {
        print(e)
        return this
    }
}