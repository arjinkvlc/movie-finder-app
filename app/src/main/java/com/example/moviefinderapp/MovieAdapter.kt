package com.example.moviefinderapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviefinderapp.models.Movie

class MovieAdapter(
    private val movies : List<Movie>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    class MovieViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
        fun bindMovie(movie : Movie){
            val movieTitle=itemView.findViewById<TextView>(R.id.movie_title)
            //val movieReleaseDate=itemView.findViewById<TextView>(R.id.movie_release_date)
            val moviePoster=itemView.findViewById<ImageView>(R.id.movie_poster)
            movieTitle.text=movie.title
            //movieReleaseDate.text=movie.release
            Log.d("deneme3",movie.poster.toString())
            Glide.with(itemView).load(IMAGE_BASE + movie.poster).into(moviePoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(movies[position])
    }
}