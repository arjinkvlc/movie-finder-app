package com.example.moviefinderapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviefinderapp.databinding.ActivitySearchPageBinding
import com.example.moviefinderapp.models.Movie
import com.example.moviefinderapp.models.MovieResponse
import com.example.moviefinderapp.services.MovieApiInterface
import com.example.moviefinderapp.services.MovieApiService
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPage : AppCompatActivity() {
    private lateinit var binding: ActivitySearchPageBinding

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_bar)
        bottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu)
        //BottomNavigationView'in arkaplan saydamlastirmasi kismi
        val background = ContextCompat.getDrawable(this, R.drawable.transparent)
        findViewById<BottomNavigationView>(R.id.bottom_navigation_bar).background = background
        //BottomNavigationView ile sayfalar arası gecis
        val moviesPage = Intent(this, HomePage::class.java).apply {
        }
        val searchPage = Intent(this, SearchPage::class.java).apply {
        }
        binding.bottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_movies -> startActivity(moviesPage)
                //R.id.navigation_search -> replaceFragment(SearchFragment())
                R.id.navigation_search -> startActivity(searchPage)
                R.id.navigation_profile -> replaceFragment(ProfileFragment("a"))
                else -> {
                }
            }
            true
        }
        //Arama yapma kısmı
        binding.searchButton.setOnClickListener() {
            Toast.makeText(this, binding.search.text.toString(), Toast.LENGTH_SHORT).show()
            binding.rvMoviesList.layoutManager = GridLayoutManager(this, 3)
            getSearchedMovieData { movies: List<Movie> ->
                binding.rvMoviesList.adapter = MovieAdapter(movies)
            }

        }
    }

    private fun getSearchedMovieData(callback: (List<Movie>) -> Unit) {
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getSearchedMovieList(binding.search.text.toString())
            .enqueue(object : Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d("movies", "failed")
                }

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
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