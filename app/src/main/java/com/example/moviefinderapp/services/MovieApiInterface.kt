package com.example.moviefinderapp.services

import com.example.moviefinderapp.models.GuestSessionResponse
import com.example.moviefinderapp.models.MovieResponse
import com.example.moviefinderapp.models.Rating
import com.example.moviefinderapp.models.RatingResponse
import retrofit2.Call
import retrofit2.http.*

interface MovieApiInterface {
    @GET("/3/movie/popular?api_key=bdfa51ccaa4f489cc344ee40f51fba3c")
    fun getPopularMovieList(): Call<MovieResponse>

    @GET("/3/movie/top_rated?api_key=bdfa51ccaa4f489cc344ee40f51fba3c")
    fun getHighRatedMovieList(): Call<MovieResponse>

    @GET("/3/movie/upcoming?api_key=bdfa51ccaa4f489cc344ee40f51fba3c")
    fun getUpcomingMovieList(): Call<MovieResponse>

    @GET("/3/search/movie?&include_adult=false&api_key=bdfa51ccaa4f489cc344ee40f51fba3c")
    fun getSearchedMovieList(@Query("query") title: String): Call<MovieResponse>

    @GET("/3/movie/{id}/videos?api_key=bdfa51ccaa4f489cc344ee40f51fba3c")
    fun getVideoTrailer(@Path(value = "id") id: String): Call<MovieResponse>

    @POST("/3/authentication/guest_session/new")
    fun createGuestSession(@Query("api_key") apiKey: String): Call<GuestSessionResponse>

    @GET("/3/movie/now_playing?api_key=bdfa51ccaa4f489cc344ee40f51fba3c")
    fun getFavouriteMovieList(): Call<MovieResponse>

    @POST("movie/{movie_id}/rating")
    fun rateMovie(
        @Path("movie_id") movieId: String?,
        @Query("api_key") apiKey: String,
        @Query("guest_session_id") guestSessionId: String,
        @Body rating: Rating
    ): Call<RatingResponse>

}