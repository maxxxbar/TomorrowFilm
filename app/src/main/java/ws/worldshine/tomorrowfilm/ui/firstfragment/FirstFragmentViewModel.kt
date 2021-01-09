package ws.worldshine.tomorrowfilm.ui.firstfragment

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ws.worldshine.tomorrowfilm.data.MovieRepository
import ws.worldshine.tomorrowfilm.db.MovieDatabaseNew
import ws.worldshine.tomorrowfilm.model.DiscoverMovieResultsItem
import ws.worldshine.tomorrowfilm.network.Rest
import ws.worldshine.tomorrowfilm.ui.detailfragment.DetailFragment
import javax.inject.Inject

@ExperimentalPagingApi
class FirstFragmentViewModel @Inject constructor(
        database: MovieDatabaseNew,
        private val sp: SharedPreferences,
        rest: Rest
) : ViewModel() {
    private val TAG = javaClass.simpleName
    private var repository: MovieRepository = MovieRepository(database, rest, sp)

    val editor: SharedPreferences.Editor = sp.edit()
    fun resetRepository(sort: String) {
        Log.d(TAG, "resetRepository: $sort")
        editor.putString("qwe", "qweqwe").apply()
        Log.d(TAG, "resetRepository:${sp.getString("qwe", null)}")

    }

    fun getMoviesAsLiveData(): LiveData<PagingData<DiscoverMovieResultsItem>> {
        return repository.resultAsLiveData().cachedIn(viewModelScope)
    }

    fun setFilmForDetailFragment(movie: DiscoverMovieResultsItem): Bundle =
            DetailFragment.setMovieBundle(movie)

}