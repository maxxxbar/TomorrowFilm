package com.example.mymovies.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R
import com.example.mymovies.databinding.FavoriteItemBinding
import com.example.mymovies.utils.loadImageWithGlide

class FavoriteAdapterViewHolder(
        private val binding: FavoriteItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(posterPath: String) {
        binding.ivFavorite.loadImageWithGlide(posterPath)
    }

    companion object {
        fun create(parent: ViewGroup): FavoriteAdapterViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.favorite_item, parent, false)
            val binding = FavoriteItemBinding.bind(view)
            return FavoriteAdapterViewHolder(binding)
        }
    }
}