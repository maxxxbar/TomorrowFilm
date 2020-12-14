package com.example.mymovies.ui.firstfragment

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovies.data.MovieRepository
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.ui.detailfragment.DetailFragment
import com.example.mymovies.utils.Extra
import javax.inject.Inject

@ExperimentalPagingApi
class FirstFragmentViewModel @Inject constructor(
        //private val repository: MovieRepository,
        private val databaseNew: MovieDatabaseNew,
        private val sortBy: String
) : ViewModel() {
    private var repository: MovieRepository = MovieRepository(databaseNew, sortBy, Extra.VOTE_COUNT_GTE)

    fun resetRepository(sort: String) {
        repository._sortBy = sort
        repository = MovieRepository(databaseNew, sort, Extra.VOTE_COUNT_GTE)
    }

    fun getMoviesAsLiveData(): LiveData<PagingData<DiscoverMovieResultsItem>> {
        return repository.resultAsLiveData().cachedIn(viewModelScope)
    }

    fun setFilmForDetailFragment(movie: DiscoverMovieResultsItem): Bundle =
            DetailFragment.setMovieBundle(movie)

}