package com.example.mymovies.adapters

import com.example.mymovies.entries.discover.movie.Result

fun interface OnFilmClickListener {
    fun onClickListener(result: Result)
}