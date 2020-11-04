package com.example.mymovies.ui.firstfragment

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovies.data.MovieRepository
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.network.ConnectionAPI
import com.example.mymovies.ui.detailfragment.DetailFragment
import com.example.mymovies.utils.Extra

@ExperimentalPagingApi
class FirstFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val connectionApi = ConnectionAPI
    private val rest = connectionApi.create
    private val database = MovieDatabaseNew.getInstance(application.applicationContext)
    private val repository: MovieRepository = MovieRepository(
            rest = rest,
            sortBy = Extra.SORT_BY_POPULARITY,
            voteCount = Extra.VOTE_COUNT_GTE,
            database)

    fun getMoviesAsLiveData(): LiveData<PagingData<DiscoverMovieResultsItem>> {
        return repository.resultAsLiveData().cachedIn(viewModelScope)
    }

    fun setFilmForDetailFragment(movie: DiscoverMovieResultsItem): Bundle? {
        return DetailFragment.setMovieBundle(movie)
    }

}