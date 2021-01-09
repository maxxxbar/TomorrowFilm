package ws.worldshine.tomorrowfilm.datasource.movie

import androidx.room.withTransaction
import ws.worldshine.tomorrowfilm.db.MovieDatabaseNew
import ws.worldshine.tomorrowfilm.model.DiscoverMovieItem
import ws.worldshine.tomorrowfilm.model.toFavoriteMovies
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
        private val database: MovieDatabaseNew
) : MovieDataSource {

    override suspend fun getMovieById(id: Int): DiscoverMovieItem? {
        return database.withTransaction {
            database.movieDao().getMovieById(id) ?: database.movieDao().getFavoriteMovieById(id)
        }
    }

    override suspend fun isFavoriteMovie(movieId: Int): Boolean {
        return database.withTransaction {
            database.movieDao().getFavoriteMovieById(movieId) != null
        }
    }

    override suspend fun insertFavoriteMovie(discoverMovieItem: DiscoverMovieItem) {
        database.withTransaction {
            database.movieDao().insertFavoriteMovie(discoverMovieItem.toFavoriteMovies())
        }
    }
}