package com.example.moviefinderapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviefinderapp.databinding.ActivitySearchPageBinding
import com.example.moviefinderapp.models.Movie
import com.example.moviefinderapp.models.MovieResponse
import com.example.moviefinderapp.services.MovieApiInterface
import com.example.moviefinderapp.services.MovieApiService
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SearchPage : AppCompatActivity() {
    private lateinit var binding: ActivitySearchPageBinding

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavigationView = binding.bottomNavigationBar
        bottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu)
        val getUsername: String? = intent.getStringExtra("username")
        //BottomNavigationView'in arkaplan saydamlastirmasi kismi
        val background = ContextCompat.getDrawable(this, R.drawable.transparent)
        bottomNavigationView.background = background
        //BottomNavigationView ile sayfalar arası gecis
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
        //Arama yapma kısmı
        binding.searchButton.setOnClickListener() {
            val database = Firebase.database
            val myRef = database.getReference("searches")
            val searchDate = LocalDateTime.now()
            val dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
            val stringDate = searchDate.format(dateFormat)
            val searchData = Searches(binding.search.text.toString(), stringDate)
            myRef.child(stringDate.toString()).setValue(searchData)
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

data class Searches(val search: String, val date: String) {

}