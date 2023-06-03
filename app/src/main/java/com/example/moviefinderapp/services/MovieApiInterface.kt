package com.example.moviefinderapp.services

import com.example.moviefinderapp.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieApiInterface {

    @GET("/3/movie/popular?api_key=bdfa51ccaa4f489cc344ee40f51fba3c")
    fun getMovieList(): Call<MovieResponse>
}