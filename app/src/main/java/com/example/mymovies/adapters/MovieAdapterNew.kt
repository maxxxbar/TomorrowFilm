package com.example.mymovies.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import com.example.mymovies.R
import com.example.mymovies.databinding.MovieItemBinding
import com.example.mymovies.entries.discover.movie.Result
import com.example.mymovies.utils.BindingExtra
import com.google.android.flexbox.AlignSelf
import com.google.android.flexbox.FlexboxLayoutManager
import kotlin.jvm.internal.Intrinsics

class MovieAdapterNew : PagingDataAdapter<Result, MovieAdapterViewHolderNew>(Result.CALLBACK) {

  var onFilmClickListener: OnFilmClickListener? = null

    inline fun setOnFilmClickListener(crossinline l: (Result) -> Unit) {
        this.onFilmClickListener = OnFilmClickListener { l(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewHolderNew {
        val binding: MovieItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.movie_item, parent, false)
        return MovieAdapterViewHolderNew(binding)
    }

    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: MovieAdapterViewHolderNew, position: Int) {

        val lp  =holder.binding.imageViewSmallPoster.layoutParams
        if (lp is FlexboxLayoutManager.LayoutParams) {
            var i = kotlin.random.Random.nextInt(1,5).toFloat()
            lp.flexGrow = 1f
            lp.alignSelf = AlignSelf.FLEX_END
            lp.flexShrink = 1f;

        }

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