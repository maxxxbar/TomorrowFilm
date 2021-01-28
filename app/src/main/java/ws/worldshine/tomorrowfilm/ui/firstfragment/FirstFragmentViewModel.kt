package ws.worldshine.tomorrowfilm.ui.firstfragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ws.worldshine.tomorrowfilm.data.MovieRepository
import ws.worldshine.tomorrowfilm.db.MovieDatabaseNew
import ws.worldshine.tomorrowfilm.model.DiscoverMovieResultsItem
import ws.worldshine.tomorrowfilm.network.Rest
import ws.worldshine.tomorrowfilm.ui.detailfragment.DetailFragment
import javax.inject.Inject

@ExperimentalPagingApi
class FirstFragmentViewModel @Inject constructor(
        database: MovieDatabaseNew,
        sp: SharedPreferences,
        rest: Rest
) : ViewModel() {
    private val TAG = javaClass.simpleName
    private var repository: MovieRepository = MovieRepository(database, rest, sp)

    fun getMoviesAsLiveData(): Flow<PagingData<DiscoverMovieResultsItem>> {
        return repository.resultAsLiveData().cachedIn(viewModelScope)
    }

    fun setFilmForDetailFragment(movie: DiscoverMovieResultsItem): Bundle =
            DetailFragment.setMovieBundle(movie)

}