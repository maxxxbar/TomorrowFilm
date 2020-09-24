package com.example.mymovies.datasource.movie

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.mymovies.entries.discover.moviesnew.DiscoverMovieResultsItem
import com.example.mymovies.network.Rest
import com.example.mymovies.utils.Extra
import kotlinx.coroutines.flow.Flow

class MovieRepository(
        private val rest: Rest,
        private val sortBy: String,
        private val voteCount: Int = Extra.VOTE_COUNT_GTE) {
    companion object {
        private const val NETWORK_PAGE_SIZE = 100
    }

    fun resultAsLiveData(): LiveData<PagingData<DiscoverMovieResultsItem>> {
        return Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true),
                pagingSourceFactory = { MoviePagingSource(rest, sortBy, voteCount) }
        ).liveData
    }

    fun getResultAsFlow(): Flow<PagingData<DiscoverMovieResultsItem>> {
        return Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
                pagingSourceFactory = { MoviePagingSource(rest, Extra.SORT_BY_POPULARITY, Extra.VOTE_COUNT_GTE) }
        ).flow
    }
}