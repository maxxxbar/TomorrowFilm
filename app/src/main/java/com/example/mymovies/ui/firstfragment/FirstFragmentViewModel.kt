package com.example.mymovies.ui.firstfragment

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovies.data.MovieRepository
import com.example.mymovies.datasource.movie.MoviePagingSource
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.network.ConnectionAPI
import com.example.mymovies.utils.Extra
import kotlinx.coroutines.flow.Flow

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

    private val pagingSourceFactory = { MoviePagingSource(rest, Extra.SORT_BY_POPULARITY, Extra.VOTE_COUNT_GTE) }

    val pagedListLiveData: LiveData<PagingData<DiscoverMovieResultsItem>> = repository.resultAsLiveData()

    fun getMoviesAsLiveData(): LiveData<PagingData<DiscoverMovieResultsItem>> {
        return repository.resultAsLiveData().cachedIn(viewModelScope)
    }

    fun searchResult(): Flow<PagingData<DiscoverMovieResultsItem>> {
        return repository.getResultAsFlow().cachedIn(viewModelScope)
    }
}