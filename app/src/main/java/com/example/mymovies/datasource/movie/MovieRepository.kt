package com.example.mymovies.datasource.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mymovies.entries.discover.movie.Result
import com.example.mymovies.network.Rest
import com.example.mymovies.utils.Extra
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val rest: Rest) {

    fun getResult(): Flow<PagingData<Result>> {
        return Pager(
                config = PagingConfig(pageSize = 30),
                pagingSourceFactory = { MoviePagingSource(rest, Extra.SORT_BY_POPULARITY, Extra.VOTE_COUNT_GTE) }
        ).flow
    }
}