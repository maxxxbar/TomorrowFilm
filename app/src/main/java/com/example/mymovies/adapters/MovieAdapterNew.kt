package com.example.mymovies.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mymovies.R
import com.example.mymovies.databinding.MovieItemBinding
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.utils.Extra
import com.google.android.flexbox.AlignSelf
import com.google.android.flexbox.FlexboxLayoutManager

class MovieAdapterNew : PagingDataAdapter<DiscoverMovieResultsItem, MovieAdapterViewHolderNew>(COMPARATOR) {

    var onFilmClickListener: OnFilmClickListener? = null

    inline fun setOnFilmClickListener(crossinline l: (DiscoverMovieResultsItem) -> Unit) {
        this.onFilmClickListener = OnFilmClickListener { l(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapterViewHolderNew {
        val binding: MovieItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.movie_item, parent, false)
        return MovieAdapterViewHolderNew(binding)
    }

    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: MovieAdapterViewHolderNew, position: Int) {
        val lp = holder.binding.imageViewSmallPoster.layoutParams
        if (lp is FlexboxLayoutManager.LayoutParams) {
            lp.flexGrow = 1f
            lp.alignSelf = AlignSelf.FLEX_END
            lp.flexShrink = 1f;

        }
        val movies = getItem(position)
        if (movies?.posterPath != null && movies.posterPath.isNotEmpty()) {
            val url = Extra.POSTER_BASE_URL + Extra.SMALL_POSTER_SIZE + movies.posterPath;
            Glide
                    .with(holder.binding.imageViewSmallPoster)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .into(holder.binding.imageViewSmallPoster);
            holder.itemView.setOnClickListener {
                onFilmClickListener?.onClickListener(movies)
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