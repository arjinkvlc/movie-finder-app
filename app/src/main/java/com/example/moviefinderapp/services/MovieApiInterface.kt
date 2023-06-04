package com.example.moviefinderapp.services

import com.example.moviefinderapp.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiInterface {
    @GET("/3/movie/popular?api_key=bdfa51ccaa4f489cc344ee40f51fba3c")
    fun getPopularMovieList(): Call<MovieResponse>

    @GET("/3/movie/top_rated?api_key=bdfa51ccaa4f489cc344ee40f51fba3c")
    fun getHighRatedMovieList(): Call<MovieResponse>

    @GET("/3/movie/upcoming?api_key=bdfa51ccaa4f489cc344ee40f51fba3c")
    fun getUpcomingMovieList(): Call<MovieResponse>

    @GET("/3/search/movie?api_key=bdfa51ccaa4f489cc344ee40f51fba3c")
    fun getSearchedMovieList(@Query("query") title:String): Call<MovieResponse>
}