package com.example.moviefinderapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviefinderapp.models.Movie
import com.example.moviefinderapp.models.MovieResponse
import com.example.moviefinderapp.services.MovieApiInterface
import com.example.moviefinderapp.services.MovieApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesFragment : Fragment() {
     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val view=inflater.inflate(R.layout.fragment_movies, container, false)
         val rv_movies_list= this.view?.findViewById<RecyclerView>(R.id.rv_movies_list)

         rv_movies_list?.layoutManager = LinearLayoutManager(context)
         rv_movies_list?.setHasFixedSize(true)
         getMovieData { movies : List<Movie> ->
             rv_movies_list?.adapter = MovieAdapter(movies)
         }



        // Inflate the layout for this fragment
        return view

     }
    private fun getMovieData(callback: (List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("movies","failed")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.d("movies",response.body().toString())
                return callback(response.body()!!.movies)
            }

        })
    }

}