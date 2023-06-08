package com.example.moviefinderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviefinderapp.databinding.ActivityProfilePageBinding
import com.example.moviefinderapp.models.Movie
import com.example.moviefinderapp.models.MovieResponse
import com.example.moviefinderapp.services.MovieApiInterface
import com.example.moviefinderapp.services.MovieApiService
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilePage : AppCompatActivity() {
    private lateinit var binding: ActivityProfilePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val logoutButton = binding.logoutButton
        //Favori Filmlerin Listelenmesi
        binding.rvMoviesList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getFavouriteMovies { movies: List<Movie> ->
            binding.rvMoviesList.adapter = MovieAdapter(movies)
        }
        val getUsername: String? = intent.getStringExtra("username")
        val usernameValue = intent.getStringExtra("username")
        if (usernameValue != null) {
            if (usernameValue.contains(".com")) {
                binding.usernameOnProfile.text = usernameValue
            }
        }
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
        //Oturum kapatma ve giris sayfasına gecis
        logoutButton.setOnClickListener() {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Log out Successfully!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getFavouriteMovies(callback: (List<Movie>) -> Unit) {
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getFavouriteMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("movies", "failed")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.d("movies", response.body().toString())
                return callback(response.body()!!.movies)
            }

        })
    }
}