package com.example.mymovies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import com.example.mymovies.R
import com.example.mymovies.databinding.MovieItemBinding
import com.example.mymovies.entries.discover.movie.Result
import com.example.mymovies.utils.BindingExtra

class MovieAdapterNew : PagedListAdapter<Result, MovieAdapterViewHolderNew>(Result.CALLBACK) {

  var onFilmClickListener: OnFilmClickListener? = null

    inline fun setOnFilmClickListener(crossinline l: (Result) -> Unit) {
        this.onFilmClickListener = OnFilmClickListener { l(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewHolderNew {
        val binding: MovieItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.movie_item, parent, false)
        return MovieAdapterViewHolderNew(binding)
    }

    override fun onBindViewHolder(holder: MovieAdapterViewHolderNew, position: Int) {
        val movies = getItem(position)
        if (movies != null && movies.posterPath != null && movies.posterPath.isNotEmpty()) {
            BindingExtra.loadImage(holder.binding.imageViewSmallPoster, movies.backdropPath)
            holder.binding.movieItem = movies
            holder.itemView.setOnClickListener {
                onFilmClickListener?.onClickListener(movies)
            }
        }
    }
}