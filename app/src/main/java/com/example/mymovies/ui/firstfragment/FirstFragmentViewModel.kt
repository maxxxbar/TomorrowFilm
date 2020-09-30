package com.example.mymovies.ui.firstfragmentLi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.mymovies.data.MovieRepository
import com.example.mymovies.datasource.movie.MoviePagingSource
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.entries.discover.movie.Result
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.network.ConnectionAPI
import com.example.mymovies.paging2.DataSourceMovieFactory
import com.example.mymovies.utils.Extra
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors

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

}