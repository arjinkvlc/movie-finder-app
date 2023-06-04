package com.example.moviefinderapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviefinderapp.databinding.ActivityHomePageBinding
import com.example.moviefinderapp.models.Movie
import com.example.moviefinderapp.models.MovieResponse
import com.example.moviefinderapp.services.MovieApiInterface
import com.example.moviefinderapp.services.MovieApiService
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        //Username'i activityden fragment'a gecirme
        val getUsername: String? = intent.getStringExtra("username")
        Toast.makeText(this, getUsername.toString(), Toast.LENGTH_SHORT).show()
        //BottomNavigationView'in menu ogelerini olusturmak icin inflateMenu metodu kullanildi
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        bottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu)
        //BottomNavigationView'in arkaplan saydamlastirmasi kismi
        val background = ContextCompat.getDrawable(this, R.drawable.transparent)
        findViewById<BottomNavigationView>(R.id.bottom_navigation_bar).background = background
        ////BottomNavigationView ile sayfalar arası gecis
        val moviesPage = Intent(this, HomePage::class.java).apply {
        }
        val searchPage = Intent(this, SearchPage::class.java).apply {
        }
        binding.bottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_movies -> startActivity(moviesPage)
                R.id.navigation_search -> startActivity(searchPage)
                R.id.navigation_profile -> replaceFragment(ProfileFragment(getUsername.toString()))
                else -> {
                }
            }
            true
        }
        //Popular Movies Listing
        binding.rvMoviesList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        //binding.rvMoviesList.setHasFixedSize(true)
        getPopularMovieData { movies: List<Movie> ->
            binding.rvMoviesList.adapter = MovieAdapter(movies)
        }
        //Highly Rated Movies Listing
        binding.rvMoviesList2.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getHighRatedMovieData { movies: List<Movie> ->
            binding.rvMoviesList2.adapter = MovieAdapter(movies)
        }
        //Upcoming Movies Listing
        binding.rvMoviesList3.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getUpcomingMovieData { movies: List<Movie> ->
            binding.rvMoviesList3.adapter = MovieAdapter(movies)
        }

    }

    private fun getPopularMovieData(callback: (List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getPopularMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("movies","failed")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.d("movies",response.body().toString())
                return callback(response.body()!!.movies)
            }

        })
    }
    private fun getHighRatedMovieData(callback: (List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getHighRatedMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("movies","failed")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.d("movies",response.body().toString())
                return callback(response.body()!!.movies)
            }

        })
    }
    private fun getUpcomingMovieData(callback: (List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getUpcomingMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("movies","failed")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.d("movies",response.body().toString())
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