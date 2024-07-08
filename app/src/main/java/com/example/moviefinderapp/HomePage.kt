package com.example.moviefinderapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.moviefinderapp.databinding.ActivityHomePageBinding
import com.example.moviefinderapp.models.Movie
import com.example.moviefinderapp.models.MovieResponse
import com.example.moviefinderapp.services.MovieApiInterface
import com.example.moviefinderapp.services.MovieApiService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomePage : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding

    @SuppressLint("CutPasteId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Username'i oturum acma sayfasindan homepage'a gecirme
        val getUsername: String? = intent.getStringExtra("username")
        //BottomNavigationView'in menu ogelerini olusturmak icin inflateMenu metodu kullanildi
        val bottomNavigationView = binding.bottomNavigationBar
        bottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu)
        //BottomNavigationView'in arkaplan saydamlastirmasi kismi
        val background = ContextCompat.getDrawable(this, R.drawable.transparent)
        bottomNavigationView.background = background
        ////BottomNavigationView ile sayfalar arası gecis
        val moviesPage = Intent(this, HomePage::class.java).apply {
            putExtra("username", getUsername)
        }
        val searchPage = Intent(this, SearchPage::class.java).apply {
            putExtra("username", getUsername)
        }
        val profilePage = Intent(this, ProfilePage::class.java).apply {
            putExtra("username", getUsername)
        }
        binding.bottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_movies -> startActivity(moviesPage)
                R.id.navigation_search -> startActivity(searchPage)
                R.id.navigation_profile -> startActivity(profilePage)
                else -> {
                }
            }
            true
        }
        //Populer Filmlerin Listelenmesi
        binding.rvMoviesList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getPopularMovieData { movies: List<Movie> ->
            binding.rvMoviesList.adapter = MovieAdapter(movies)
        }
        //Yuksek Oy Alan Filmlerin Listelenmesi
        binding.rvMoviesList2.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getHighRatedMovieData { movies: List<Movie> ->
            binding.rvMoviesList2.adapter = MovieAdapter(movies)
        }
        //Yaklasan Filmlerin Listelenmesi
        binding.rvMoviesList3.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getUpcomingMovieData { movies: List<Movie> ->
            binding.rvMoviesList3.adapter = MovieAdapter(movies)
        }

    }

    private fun getPopularMovieData(callback: (List<Movie>) -> Unit) {
        val apiService = MovieApiService.getInstance(applicationContext).create(MovieApiInterface::class.java)
        apiService.getPopularMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("movies", "failed")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.d("movies", response.body().toString())
                return callback(response.body()!!.movies)
            }

        })
    }


    private fun getHighRatedMovieData(callback: (List<Movie>) -> Unit) {
        val apiService = MovieApiService.getInstance(applicationContext).create(MovieApiInterface::class.java)
        apiService.getHighRatedMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("movies", "failed")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.d("movies", response.body().toString())
                return callback(response.body()!!.movies)
            }

        })
    }

    private fun getUpcomingMovieData(callback: (List<Movie>) -> Unit) {
        val apiService = MovieApiService.getInstance(applicationContext).create(MovieApiInterface::class.java)
        apiService.getUpcomingMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("movies", "failed")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.d("movies", response.body().toString())
                return callback(response.body()!!.movies)
            }

        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val frangmentManager = supportFragmentManager
        val fragmentTransaction = frangmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}