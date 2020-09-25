package com.example.mymovies.adapters

import com.example.mymovies.model.DiscoverMovieResultsItem

fun interface OnFilmClickListener {
    fun onClickListener(result: DiscoverMovieResultsItem)
}