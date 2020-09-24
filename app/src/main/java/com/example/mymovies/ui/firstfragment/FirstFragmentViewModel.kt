package com.example.mymovies.ui.firstfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mymovies.datasource.movie.MoviePagingSource
import com.example.mymovies.datasource.movie.MovieRepository
import com.example.mymovies.entries.discover.moviesnew.DiscoverMovieResultsItem
import com.example.mymovies.network.ConnectionAPI
import com.example.mymovies.utils.Extra
import kotlinx.coroutines.flow.Flow

class FirstFragmentViewModel() : ViewModel() {

    private val connectionApi = ConnectionAPI
    private val rest = connectionApi.create
    private val repository: MovieRepository = MovieRepository(rest = rest, sortBy = Extra.SORT_BY_POPULARITY, voteCount = Extra.VOTE_COUNT_GTE)

    private val pagingSourceFactory = { MoviePagingSource(rest, Extra.SORT_BY_POPULARITY, Extra.VOTE_COUNT_GTE) }

    val pagedListLiveData: LiveData<PagingData<DiscoverMovieResultsItem>> = repository.resultAsLiveData()

    fun getMoviesAsLiveData(): LiveData<PagingData<DiscoverMovieResultsItem>> {
        return repository.resultAsLiveData().cachedIn(viewModelScope)
    }

    fun searchResult(): Flow<PagingData<DiscoverMovieResultsItem>> {
        return repository.getResultAsFlow().cachedIn(viewModelScope)
    }
}