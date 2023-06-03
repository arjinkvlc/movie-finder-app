package com.example.moviefinderapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide


class MovieDetail() : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        val title = findViewById<TextView>(R.id.detail_title)
        val poster = findViewById<ImageView>(R.id.detail_poster)
        val overview = findViewById<TextView>(R.id.detail_overview)
        val release=findViewById<TextView>(R.id.detail_release)
        val rate=findViewById<TextView>(R.id.detail_rate)
        title.text = intent.getStringExtra("putTitle")
        overview.text = intent.getStringExtra("putOverview")
        val posterUrl = intent.getStringExtra("putPoster")
        Glide.with(this).load(posterUrl).into(poster)
        release.text=intent.getStringExtra("putRelease")
        val combineRate=intent.getStringExtra("putRate")
        rate.setText(combineRate+" / 10")


    }
}