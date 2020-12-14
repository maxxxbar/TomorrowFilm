package com.example.mymovies.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.mymovies.datasource.MoviePagingSource
import com.example.mymovies.datasource.MovieRemoteMediator
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.utils.Extra
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class MovieRepository @Inject constructor(
        private val database: MovieDatabaseNew,
        sortBy: String,
        voteCount: Int
) {
    var _sortBy: String = ""
        set(value) {
            if (value.isEmpty()) Extra.SORT_BY_POPULARITY else field = value
        }
    private val TAG = javaClass.simpleName
    private val moviePagingSource = MoviePagingSource(sortBy, voteCount)
    private val config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true, initialLoadSize = 40)
    private val remoteMediator = MovieRemoteMediator(database, sortBy, voteCount)
    private val pagingSourceFactory = { database.movieDao().getAllMovies() }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    init {
        Log.d(TAG, "has code remoteMediator: ${remoteMediator.hashCode()}")
        Log.d(TAG, "hash code moviePagingSource:${moviePagingSource.hashCode()} ")
    }


    fun resultAsLiveData(): LiveData<PagingData<DiscoverMovieResultsItem>> {
        return Pager(
                config = config,
                remoteMediator = remoteMediator,
                pagingSourceFactory = pagingSourceFactory
        ).liveData
    }

    fun getResultAsFlow(): Flow<PagingData<DiscoverMovieResultsItem>> {
        return Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
                pagingSourceFactory = { moviePagingSource }
        ).flow
    }

}