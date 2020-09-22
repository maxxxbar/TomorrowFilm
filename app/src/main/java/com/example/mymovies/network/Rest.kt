package com.example.mymovies.network

import com.example.mymovies.entries.discover.movie.Movies
import com.example.mymovies.entries.discover.moviesnew.DiscoverMovie
import com.example.mymovies.entries.discover.reviews.Reviews
import com.example.mymovies.entries.discover.trailer.Trailer
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Rest {
    @GET("/3/discover/movie")
    fun getMovies(@Query("api_key") apikey: String,
                  @Query("language") language: String,
                  @Query("sort_by") sortBy: String,
                  @Query("vote_count.gte") voteCount: Int,
                  @Query("page") page: Int): Single<Movies>

    @GET("/3/discover/movie")
   suspend fun getMovies2(@Query("api_key") apikey: String,
                  @Query("language") language: String,
                  @Query("sort_by") sortBy: String,
                  @Query("vote_count.gte") voteCount: Int,
                  @Query("page") page: Int): Response<DiscoverMovie>

    @GET("/3/movie/{movie_id}/videos")
    fun getTrailer(
            @Path("movie_id") movieId: Int,
            @Query("api_key") apikey: String,
            @Query("language") language: String
    ): Single<Trailer>

    @GET("")
    fun getReviews(
            @Path("movie_id") movieId: Int,
            @Query("api_key") apikey: String,
            @Query("language") language: String
    ): Single<Reviews>
}