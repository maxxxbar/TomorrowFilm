package com.example.mymovies.network

import com.example.mymovies.entries.discover.movie.Movies
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Rest {
    @GET("/3/discover/movie")
    fun getMovies(@Query("api_key") apikey: String,
                  @Query("language") language: String,
                  @Query("sort_by") sortBy: String,
                  @Query("vote_count.gte") voteCount: Int,
                  @Query("page") page: Int): Single<Movies>
}