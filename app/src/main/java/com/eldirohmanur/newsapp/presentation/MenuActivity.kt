package com.eldirohmanur.newsapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eldirohmanur.movie_app.presentation.MovieActivity
import com.eldirohmanur.newsapp.databinding.ActivityMenuBinding
import com.hi.dhl.binding.viewbind

class MenuActivity: AppCompatActivity() {
    private val binding: ActivityMenuBinding by viewbind()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            btnMovie.setOnClickListener {
                val intent = Intent(this@MenuActivity, MovieActivity::class.java)
                startActivity(intent)
            }

            btnNews.setOnClickListener {
                val intent = Intent(this@MenuActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}