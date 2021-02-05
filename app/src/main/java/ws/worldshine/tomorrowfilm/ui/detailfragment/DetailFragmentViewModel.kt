package ws.worldshine.tomorrowfilm.ui.detailfragment

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
    val canceled = MutableStateFlow(false)
    val isFavoriteMovies = MutableStateFlow(false)
    private val repository = MovieRepository(database, rest, sp)
    private val TAG = javaClass.simpleName
    private var job: Job? = null
    var cacheItem: DiscoverMovieItem? = null

    fun checkInFavorite(movieId: Int) {
        viewModelScope.launch {
            isFavoriteMovies.value = isFavorite(movieId)
        }
    }

    suspend fun getMovie(id: Int): DiscoverMovieItem? {
        return repository.getMovie(id)
    }

    suspend fun getTrailers(movieId: Int): List<Result>? {
        return repository.getTrailers(movieId)?.results
    }

    private suspend fun isFavorite(movieId: Int): Boolean {
        return repository.isFavoriteMovie(movieId)
    }

    suspend fun insertFavoriteMovie(discoverMovieItem: DiscoverMovieItem) {
        repository.insertFavoriteMovie(discoverMovieItem)
        isFavoriteMovies.value = true
    }

    private suspend fun deleteFavoriteMovie(favoriteMovies: DiscoverMovieItem) {
        repository.deleteFavoriteMovie(favoriteMovies)
        isFavoriteMovies.value = false
    }

    fun deleteFavoriteMovieFromDatabase(favoriteMovies: DiscoverMovieItem, showSnackBar: () -> Unit) {
        var deleted = false
        showSnackBar()
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            canceled.collectLatest {
                if (canceled.value) {
                    insertFavoriteMovie(favoriteMovies)
                    canceled.value = false
                }
                if (!it && !deleted) {
                    deleteFavoriteMovie(favoriteMovies)
                    deleted = true
                }
            }
        }
    }

    fun addMovieToFavoriteTable(showSnackBar: () -> Unit) {
        job?.cancel()
        job = viewModelScope.launch {
            cacheItem?.let { discoverItem ->
                if (isFavoriteMovies.value) {
                    deleteFavoriteMovieFromDatabase(discoverItem) { showSnackBar() }
                } else {
                    insertFavoriteMovie(discoverItem)
                }
            }
        }
    }

}