package com.example.mymovies.ui.firstfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.mymovies.utils.Extra
import com.example.mymovies.datasource.movie.DataSourceMovieFactory
import com.example.mymovies.entries.discover.movie.Result
import com.example.mymovies.network.ConnectionAPI
import java.util.concurrent.Executors

class FirstFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val connectionApi = ConnectionAPI
    private val rest = connectionApi.create
    private val executor = Executors.newCachedThreadPool()
    private val dataSourceMovieFactory = DataSourceMovieFactory(rest, Extra.SORT_BY_POPULARITY, Extra.VOTE_COUNT_GTE)
    private val config = Config(pageSize = 20)
    val pagedListLiveData: LiveData<PagedList<Result>> = dataSourceMovieFactory.toLiveData(config = config, fetchExecutor = executor)

}