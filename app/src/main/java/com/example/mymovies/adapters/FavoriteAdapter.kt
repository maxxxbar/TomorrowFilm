package com.example.mymovies.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.mymovies.model.FavoriteMovies

class FavoriteAdapter : PagingDataAdapter<FavoriteMovies, FavoriteAdapterViewHolder>(COMPARATOR) {
    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<FavoriteMovies>() {
            override fun areItemsTheSame(oldItem: FavoriteMovies, newItem: FavoriteMovies): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteMovies, newItem: FavoriteMovies): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: FavoriteAdapterViewHolder, position: Int) {
        val favoriteMovie = getItem(position)
        favoriteMovie?.let { favoriteMovies ->
            favoriteMovies.posterPath?.let {
                holder.bind(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapterViewHolder {
        return FavoriteAdapterViewHolder.create(parent)
    }


}
