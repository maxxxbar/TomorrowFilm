package com.example.mymovies.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.mymovies.R
import com.example.mymovies.databinding.MovieItemBinding
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.utils.Extra
import com.example.mymovies.utils.loadImageWithGlide
import com.example.mymovies.viewholders.MovieAdapterViewHolderNew
import com.google.android.flexbox.AlignSelf
import com.google.android.flexbox.FlexboxLayoutManager

class MovieAdapter : PagingDataAdapter<DiscoverMovieResultsItem, MovieAdapterViewHolderNew>(COMPARATOR) {

    private var onClickListener: OnClickListenerForMovieAdapter? = null

    fun setOnFilmClickListener(l: (DiscoverMovieResultsItem) -> Unit) {
        this.onClickListener = OnClickListenerForMovieAdapter { l(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewHolderNew {
        val binding: MovieItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.movie_item, parent, false)
        return MovieAdapterViewHolderNew(binding)
    }

    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: MovieAdapterViewHolderNew, position: Int) {
        val lp = holder.binding.imageViewSmallPoster.layoutParams
        if (lp is FlexboxLayoutManager.LayoutParams) {
            lp.apply {
                flexGrow = 1f
                alignSelf = AlignSelf.FLEX_END
                flexShrink = 1f;
            }
        }
        val movies = getItem(position)
        if (movies?.posterPath != null && movies.posterPath.isNotEmpty()) {
            holder.apply {
                binding.imageViewSmallPoster.loadImageWithGlide(movies.posterPath)
                itemView.setOnClickListener {
                    onClickListener?.onClickListener(movies)
                }
            }
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<DiscoverMovieResultsItem>() {

            override fun areItemsTheSame(oldItem: DiscoverMovieResultsItem, newItem: DiscoverMovieResultsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DiscoverMovieResultsItem, newItem: DiscoverMovieResultsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}