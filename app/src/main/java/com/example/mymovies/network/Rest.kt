package com.example.mymovies.network

import com.example.mymovies.entries.discover.movie.Movies
import com.example.mymovies.entries.discover.moviesnew.DiscoverMovie
import com.example.mymovies.entries.discover.reviews.Reviews
import com.example.mymovies.entries.discover.trailer.Trailer
import com.example.mymovies.utils.Extra
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Rest {

    companion object {
        private const val API_KEY = "api_key"
        private const val LANGUAGE = "language"
        private const val SORT_BY = "sort_by"
        private const val VOTE_COUNT_GTE = "vote_count.gte"
        private const val PAGE = "page"


    }

    @GET("/3/discover/movie")
    fun getMovies(@Query("api_key") apikey: String,
                  @Query("language") language: String,
                  @Query("sort_by") sortBy: String,
                  @Query("vote_count.gte") voteCount: Int,
                  @Query("page") page: Int): Single<Movies>

    @GET("/3/discover/movie")
    suspend fun getMovies2(
            @Query(API_KEY) apikey: String = Extra.API_KEY,
            @Query(LANGUAGE) language: String = Extra.LANGUAGE,
            @Query(SORT_BY) sortBy: String,
            @Query(VOTE_COUNT_GTE) voteCount: Int,
            @Query(PAGE) page: Int): Response<DiscoverMovie>

    @GET("/3/discover/movie")
    suspend fun getMovies3(
            @Query(API_KEY) apikey: String = Extra.API_KEY,
            @Query(LANGUAGE) language: String = Extra.LANGUAGE,
            @Query(SORT_BY) sortBy: String,
            @Query(VOTE_COUNT_GTE) voteCount: Int,
            @Query(PAGE) page: Int): Observable<DiscoverMovie>

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