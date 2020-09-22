package com.example.mymovies.ui.firstfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.mymovies.datasource.movie.DataSourceMovieFactory
import com.example.mymovies.datasource.movie.MoviePagingSource
import com.example.mymovies.datasource.movie.MovieRepository
import com.example.mymovies.entries.discover.moviesnew.DiscoverMovieResultsItem
import com.example.mymovies.network.ConnectionAPI
import com.example.mymovies.utils.Extra
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors

class FirstFragmentViewModel() : ViewModel() {

    private val connectionApi = ConnectionAPI
    private val rest = connectionApi.create
    private val executor = Executors.newCachedThreadPool()
    private val dataSourceMovieFactory = DataSourceMovieFactory(rest, Extra.SORT_BY_POPULARITY, Extra.VOTE_COUNT_GTE)
    private val config = PagingConfig(pageSize = 100)
    private val repository: MovieRepository = MovieRepository(rest)



/*
    val pagedListLiveData: LiveData<PagingData<Result>> = dataSourceMovieFactory.toLiveData(config = config, fetchExecutor = executor)
*/


    private val pagingSourceFactory = { MoviePagingSource(rest, Extra.SORT_BY_POPULARITY, Extra.VOTE_COUNT_GTE) }

    val pagedListLiveData2: LiveData<PagingData<DiscoverMovieResultsItem>> = Pager(config = config, pagingSourceFactory = pagingSourceFactory).liveData

    fun getMoviesAsLiveData(): LiveData<PagingData<DiscoverMovieResultsItem>> {
        return repository.resultAsLiveData().cachedIn(viewModelScope)
    }

    fun searchResult(): Flow<PagingData<DiscoverMovieResultsItem>> {
        return repository.getResultAsFlow().cachedIn(viewModelScope)
    }
}


