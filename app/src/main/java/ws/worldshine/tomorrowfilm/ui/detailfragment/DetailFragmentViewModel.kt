package ws.worldshine.tomorrowfilm.ui.detailfragment

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import ws.worldshine.tomorrowfilm.data.MovieRepository
import ws.worldshine.tomorrowfilm.db.MovieDatabaseNew
import ws.worldshine.tomorrowfilm.discover.trailer.Result
import ws.worldshine.tomorrowfilm.model.DiscoverMovieItem
import ws.worldshine.tomorrowfilm.network.Rest
import javax.inject.Inject

class DetailFragmentViewModel @Inject constructor(
        database: MovieDatabaseNew,
        rest: Rest,
        sp: SharedPreferences
) : ViewModel() {

    private val repository = MovieRepository(database, rest, sp)
    private val TAG = javaClass.simpleName

    suspend fun getMovie(id: Int): DiscoverMovieItem? {
        return repository.getMovie(id)
    }

    suspend fun getTrailers(movieId: Int): List<Result> {
        return repository.getTrailers(movieId).results
    }

    suspend fun isFavoriteMovie(movieId: Int): Boolean {
        return repository.isFavoriteMovie(movieId)
    }

    suspend fun insertFavoriteMovie(discoverMovieItem: DiscoverMovieItem) {
        repository.insertFavoriteMovie(discoverMovieItem)
    }

}