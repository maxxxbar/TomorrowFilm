package com.example.mymovies.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.mymovies.datasource.FavoriteDataSource
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.FavoriteMovies
import javax.inject.Inject

class FavoriteRepository @Inject constructor (private val favoriteDataSource: FavoriteDataSource) {
    private val config = PagingConfig(pageSize = 10)
    private val pagingSourceFactory = { favoriteDataSource }
    fun getFavoriteMovies(): LiveData<PagingData<FavoriteMovies>> {
        return Pager(
                config = config,
                pagingSourceFactory = pagingSourceFactory
        ).liveData
    }
}