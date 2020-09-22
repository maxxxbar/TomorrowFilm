package com.example.mymovies.adapters

import com.example.mymovies.entries.discover.moviesnew.DiscoverMovieResultsItem

fun interface OnFilmClickListener {
    fun onClickListener(result: DiscoverMovieResultsItem)
}