package com.example.moviefinderapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.moviefinderapp.databinding.ActivityMovieDetailBinding
import com.example.moviefinderapp.models.GuestSessionResponse
import com.example.moviefinderapp.models.Movie
import com.example.moviefinderapp.models.MovieResponse
import com.example.moviefinderapp.models.Rating
import com.example.moviefinderapp.services.MovieApiInterface
import com.example.moviefinderapp.services.MovieApiService
import retrofit2.*


class MovieDetail() : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Secilen film bilgilerinin atanmasi
        val title = binding.detailTitle
        val poster = binding.detailPoster
        poster.setColorFilter(Color.rgb(100, 100, 100), android.graphics.PorterDuff.Mode.MULTIPLY)
        val playButton = binding.playTrailerImage
        val overview = binding.detailOverview
        val release = binding.detailRelease
        val rate = binding.detailRate
        title.text = intent.getStringExtra("putTitle")
        overview.text = intent.getStringExtra("putOverview")
        val posterUrl = intent.getStringExtra("putPoster")
        Glide.with(this).load(posterUrl).into(poster)
        release.text = intent.getStringExtra("putRelease")
        val combineRate = intent.getStringExtra("putRate")
        val movieId = intent.getStringExtra("putId")
        rate.setText(combineRate + " / 10")
        //Fragmana yonlendirme
        playButton.setOnClickListener() {
            getVideoTrailer { movies: List<Movie> ->
                val keyLink: String?
                if (movies.isNotEmpty()) {
                    keyLink = movies[movies.size - 1].key
                } else {
                    keyLink = ""
                }
                if (keyLink != "") {
                    val url = "https://www.youtube.com/watch?v=$keyLink"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "This movie does not have any trailer.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        }
        //Film oylama
        val ratingButton = binding.rateButton
        val rateValue = binding.ratingInput
        ratingButton.setOnClickListener() {
            val getRate = (rateValue.rating * 2.0)

            val apiKey = "bdfa51ccaa4f489cc344ee40f51fba3c"
            val guestSessionUrl =
                "https://api.themoviedb.org/3/authentication/guest_session/new?api_key=$apiKey"
            val apiService = MovieApiService.getInstance(applicationContext).create(MovieApiInterface::class.java)
            apiService.createGuestSession(apiKey).enqueue(object : Callback<GuestSessionResponse> {
                override fun onResponse(
                    call: Call<GuestSessionResponse>,
                    response: Response<GuestSessionResponse>
                ) {
                    if (response.isSuccessful) {
                        val guestSessionId = response.body()?.guestSessionId
                        val rating = Rating(getRate)
                        if (guestSessionId != null) {
                            Log.d("rateTestMovieID", movieId.toString())
                            Log.d("rateTestGuestID", guestSessionId.toString())
                            Log.d("rateTestRating", rating.toString())
                            apiService.rateMovie(movieId, apiKey, guestSessionId, rating)
                        }
                    } else {
                        Log.d("testguest", "failed")
                    }
                }

                override fun onFailure(call: Call<GuestSessionResponse>, t: Throwable) {
                    Log.d("guest", "failed")
                }
            })

            Toast.makeText(this, "Rated Successfully: ${getRate.toInt()}", Toast.LENGTH_SHORT)
                .show()
        }
        //Favorilere ekleme
        var favStatus = 0
        val favButton = binding.addFavButton
        favButton.setOnClickListener() {
            if (favStatus == 0) {
                favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                Toast.makeText(this, "Added to favourite list", Toast.LENGTH_SHORT).show()
                favStatus = 1
            } else if (favStatus == 1) {
                favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                Toast.makeText(this, "Removed from favourite list", Toast.LENGTH_SHORT).show()
                favStatus = 0
            }
            //favButton.setCompoundDrawables(R.drawable.ic_baseline_favorite_24,0,0,0)
        }

    }

    private fun getVideoTrailer(callback: (List<Movie>) -> Unit) {
        val apiService = MovieApiService.getInstance(applicationContext).create(MovieApiInterface::class.java)
        apiService.getVideoTrailer(intent.getStringExtra("putId").toString())
            .enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("videos", "failed")
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    Log.d("videos", response.body().toString())
                    return callback(response.body()!!.movies)
                }

            })
    }
}