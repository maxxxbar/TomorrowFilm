package ws.worldshine.tomorrowfilm.ui.favoritefragment

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ws.worldshine.tomorrowfilm.data.FavoriteListRepository
import ws.worldshine.tomorrowfilm.model.FavoriteMovies
import ws.worldshine.tomorrowfilm.ui.detailfragment.DetailFragment
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
        private val favoriteRepository: FavoriteListRepository
) : ViewModel() {


    fun getFavoriteMovies(): LiveData<PagingData<FavoriteMovies>> {
        return favoriteRepository.getFavoriteMovies().cachedIn(viewModelScope)
    }

    fun setFavoriteMovieForDetailFragment(favoriteMovie: FavoriteMovies): Bundle =
            DetailFragment.setMovieBundle(favoriteMovie)
}