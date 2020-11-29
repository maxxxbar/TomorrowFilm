package com.example.mymovies.network

import com.example.mymovies.BuildConfig
import com.example.mymovies.entries.discover.movie.Movies
import com.example.mymovies.entries.discover.reviews.Reviews
import com.example.mymovies.entries.discover.trailer.Trailer
import com.example.mymovies.model.DiscoverMovie
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Rest {

    companion object {
        private const val API_KEY_QUERY = "api_key"
        private const val LANGUAGE_QUERY = "language"
        private const val SORT_BY_QUERY = "sort_by"
        private const val VOTE_COUNT_GTE_QUERY = "vote_count.gte"
        private const val PAGE_QUERY = "page"
        private const val MOVIE_ID_QUERY = "movie_id"
        private const val API_KEY_VALUE = BuildConfig.API_KEY_TMDB
        private const val LANGUAGE_VALUE = "ru-RU"
    }

    @GET("/3/discover/movie")
    suspend fun getMovies(
            @Query(API_KEY_QUERY) apikey: String = API_KEY_VALUE,
            @Query(LANGUAGE_QUERY) language: String = LANGUAGE_VALUE,
            @Query(SORT_BY_QUERY) sortBy: String,
            @Query(VOTE_COUNT_GTE_QUERY) voteCount: Int,
            @Query(PAGE_QUERY) page: Int): Response<DiscoverMovie>

    @GET("/3/movie/{movie_id}/videos")
    fun getTrailer(
            @Path(MOVIE_ID_QUERY) movieId: Int,
            @Query(API_KEY_QUERY) apikey: String = API_KEY_VALUE,
            @Query(LANGUAGE_QUERY) language: String = LANGUAGE_VALUE
    ): Single<Trailer>

    @GET("/3/discover/movie")
    fun getReviews(
            @Path(MOVIE_ID_QUERY) movieId: Int,
            @Query(API_KEY_QUERY) apikey: String = API_KEY_VALUE,
            @Query(LANGUAGE_QUERY) language: String = LANGUAGE_VALUE
    ): Single<Reviews>
}