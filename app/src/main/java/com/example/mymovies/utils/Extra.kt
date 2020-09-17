package com.example.mymovies.utils

class Extra {
    companion object{
        const val BASE_URL = "https://api.themoviedb.org"
        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/"
        const val SMALL_POSTER_SIZE = "w300"
        const val BIG_POSTER_SIZE = "w780"
        const val API_KEY = "74f19a7f035e1b43395bec79650b05a8"
        const val LANGUAGE = "ru-RU"
        const val INCLUDE_ADULT = false
        const val INCLUDE_VIDEO = false
        const val SORT_BY_POPULARITY = "popularity.desc"
        const val SORT_BY_VOTE_AVERAGE = "vote_average.desc"
        const val VOTE_COUNT_GTE = 100
        const val SHARED_PREFERENCES_NAME = "APP_PREFERENCES"
        const val DB_NAME = "movies.db"
    }
}