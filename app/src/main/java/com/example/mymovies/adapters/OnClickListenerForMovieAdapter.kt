package com.example.mymovies.adapters

import com.example.mymovies.model.DiscoverMovieResultsItem

fun interface OnClickListenerForMovieAdapter {
    fun onClickListener(result: DiscoverMovieResultsItem)
}