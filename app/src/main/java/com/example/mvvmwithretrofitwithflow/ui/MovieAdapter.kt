package com.example.mvvmwithretrofitwithflow.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmwithretrofitwithflow.data.Movie
import com.example.mvvmwithretrofitwithflow.databinding.AdapterMovieBinding
import javax.inject.Inject


class MovieAdapter @Inject constructor() : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var movies = mutableListOf<Movie>()
    private var clickInterface: ClickInterface<Movie>? = null

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies.toMutableList()
        notifyItemRangeInserted(0, movies.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            AdapterMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.view.tvTitle.text = movie.title
        holder.view.rvYear.text = "Release Date : ${movie.releaseDate}"
        holder.view.rvRating.text = "Average Vote : ${movie.voteAverage}"
        val imagePath = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"
        Glide
            .with(holder.view.imgMovieImage.context)
            .load(imagePath)
            .centerCrop()
            .into(holder.view.imgMovieImage)
        holder.view.movieCard.setOnClickListener {
            clickInterface?.onClick(movie)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setItemClick(clickInterface: ClickInterface<Movie>) {
        this.clickInterface = clickInterface
    }

    class MovieViewHolder(val view: AdapterMovieBinding) : RecyclerView.ViewHolder(view.root)
}

interface ClickInterface<T> {
    fun onClick(data: T)
}