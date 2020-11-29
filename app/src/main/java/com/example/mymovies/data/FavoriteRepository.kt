package com.example.mymovies.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.mymovies.db.MovieDatabaseNew
import com.example.mymovies.model.FavoriteMovies

class FavoriteRepository(private val db: MovieDatabaseNew) {
    private val config = PagingConfig(pageSize = 10)
    private val pagingSourceFactory = { FavoriteDataSource(db) }
    fun getFavoriteMovies(): LiveData<PagingData<FavoriteMovies>> {
        return Pager(
                config = config,
                pagingSourceFactory = pagingSourceFactory
        ).liveData
    }
}