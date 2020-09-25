package com.example.mymovies.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.mymovies.datasource.movie.MoviePagingSource
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.network.Rest
import com.example.mymovies.utils.Extra
import kotlinx.coroutines.flow.Flow

class MovieRepository(
        private val rest: Rest,
        private val sortBy: String = Extra.SORT_BY_POPULARITY,
        private val voteCount: Int = Extra.VOTE_COUNT_GTE,
        private val database: MovieDatabaseNew) {
    companion object {
        private const val NETWORK_PAGE_SIZE = 100
    }

    private val pagingSourceFactory = {}

    @ExperimentalPagingApi
    fun resultAsLiveData(): LiveData<PagingData<DiscoverMovieResultsItem>> {
        return Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true),
                remoteMediator = MovieRemoteMediator(rest, database, sortBy, voteCount),
                pagingSourceFactory = { MoviePagingSource(rest, Extra.SORT_BY_POPULARITY, Extra.VOTE_COUNT_GTE) }
        ).liveData
    }

    fun getResultAsFlow(): Flow<PagingData<DiscoverMovieResultsItem>> {
        return Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
                pagingSourceFactory = { MoviePagingSource(rest, Extra.SORT_BY_POPULARITY, Extra.VOTE_COUNT_GTE) }
        ).flow
    }
}