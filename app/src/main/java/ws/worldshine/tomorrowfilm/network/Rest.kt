package ws.worldshine.tomorrowfilm.network

import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ws.worldshine.tomorrowfilm.BuildConfig
import ws.worldshine.tomorrowfilm.discover.reviews.Reviews
import ws.worldshine.tomorrowfilm.discover.trailer.Trailer
import ws.worldshine.tomorrowfilm.model.DiscoverMovie

interface Rest {

    private companion object {
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

    @GET("/3/discover/movie")
    suspend fun getMovies2(
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

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getTrailers(
            @Path(MOVIE_ID_QUERY) movieId: Int,
            @Query(LANGUAGE_QUERY) language: String = LANGUAGE_VALUE
    ): Response<Trailer>

    @GET("/3/discover/movie")
    fun getReviews(
            @Path(MOVIE_ID_QUERY) movieId: Int,
            @Query(API_KEY_QUERY) apikey: String = API_KEY_VALUE,
            @Query(LANGUAGE_QUERY) language: String = LANGUAGE_VALUE
    ): Single<Reviews>
}