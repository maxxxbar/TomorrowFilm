package com.example.mymovies.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.mymovies.datasource.MoviePagingSource
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.network.Rest
import com.example.mymovies.utils.Extra
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class MovieRepository(
        private val rest: Rest,
        private val sortBy: String = Extra.SORT_BY_POPULARITY,
        private val voteCount: Int = Extra.VOTE_COUNT_GTE,
        private val database: MovieDatabaseNew) {

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    private val config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true, initialLoadSize = 40)
    private val remoteMediator = MovieRemoteMediator(rest, database, sortBy, voteCount)
    private val pagingSourceFactory = { database.movieDao().getAllMovies() }

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
                pagingSourceFactory = { MoviePagingSource(rest, sortBy, voteCount) }
        ).flow
    }
}