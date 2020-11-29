package com.example.mymovies.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R
import com.example.mymovies.databinding.FavoriteItemBinding
import com.example.mymovies.utils.loadImageWithGlide
import com.google.android.flexbox.FlexboxLayoutManager

class FavoriteAdapterViewHolder(
        private val binding: FavoriteItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(posterPath: String) {
        val lp = binding.ivFavorite.layoutParams
        if (lp is FlexboxLayoutManager.LayoutParams) {
            lp.apply {
                flexGrow = 1f
                flexShrink = 1f;
            }
        }
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