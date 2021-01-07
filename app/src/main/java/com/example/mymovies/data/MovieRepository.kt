package com.example.mymovies.data

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.mymovies.datasource.MovieRemoteMediator
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.network.Rest
import javax.inject.Inject

@ExperimentalPagingApi
class MovieRepository @Inject constructor(
        database: MovieDatabaseNew,
        rest: Rest,
        sp: SharedPreferences
) {

    private val TAG = javaClass.simpleName
    private val config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true, initialLoadSize = 40)
    private val remoteMediator = MovieRemoteMediator(database, rest, sp)
    private val pagingSourceFactory = { database.movieDao().getAllMovies() }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    fun resultAsLiveData(): LiveData<PagingData<DiscoverMovieResultsItem>> {
        return Pager(
                config = config,
                remoteMediator = remoteMediator,
                pagingSourceFactory = pagingSourceFactory
        ).liveData
    }

}