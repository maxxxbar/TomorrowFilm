package ws.worldshine.tomorrowfilm.data

import android.content.SharedPreferences
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ws.worldshine.tomorrowfilm.datasource.MovieRemoteMediator
import ws.worldshine.tomorrowfilm.datasource.movie.MovieDataSourceImpl
import ws.worldshine.tomorrowfilm.datasource.trailer.TrailersDataSourceImpl
import ws.worldshine.tomorrowfilm.db.MovieDatabaseNew
import ws.worldshine.tomorrowfilm.discover.trailer.Trailer
import ws.worldshine.tomorrowfilm.model.DiscoverMovieItem
import ws.worldshine.tomorrowfilm.model.DiscoverMovieResultsItem
import ws.worldshine.tomorrowfilm.network.Rest
import javax.inject.Inject

class MovieRepository @Inject constructor(
        database: MovieDatabaseNew,
        rest: Rest,
        sp: SharedPreferences
) {

    private val TAG = javaClass.simpleName
    private val config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true, initialLoadSize = 20)

    @ExperimentalPagingApi
    private val remoteMediator = MovieRemoteMediator(database, rest, sp)
    private val pagingSourceFactory = { database.movieDao().getAllMovies() }
    private val movieDataSource = MovieDataSourceImpl(database)
    private val trailerDataSource = TrailersDataSourceImpl(rest)

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    @ExperimentalPagingApi
    fun resultAsLiveData(): Flow<PagingData<DiscoverMovieResultsItem>> {
        return Pager(
                config = config,
                remoteMediator = remoteMediator,
                pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun isFavoriteMovie(movieId: Int): Boolean {
        return movieDataSource.isFavoriteMovie(movieId)
    }

    suspend fun getTrailers(movieId: Int): Trailer? {
        return trailerDataSource.getTrailers(movieId)
    }

    suspend fun getMovie(id: Int): DiscoverMovieItem? {
        return movieDataSource.getMovieById(id)
    }

    suspend fun insertFavoriteMovie(discoverMovieItem: DiscoverMovieItem) {
        movieDataSource.insertFavoriteMovie(discoverMovieItem)
    }

    suspend fun deleteFavoriteMovie(favoriteMovies: DiscoverMovieItem) {
        movieDataSource.deleteFavoriteMovie(favoriteMovies)
    }

}