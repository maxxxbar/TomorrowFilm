package com.example.mymovies.adapters

import com.example.mymovies.model.FavoriteMovies

fun interface OnClickListenerForFavoriteAdapter {
    fun onClickListener(movie: FavoriteMovies)
}