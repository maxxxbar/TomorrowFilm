package com.example.mymovies.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymovies.model.DiscoverMovieResultsItem

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<DiscoverMovieResultsItem>)

    @Query("SELECT * FROM discover_movies")
    fun getAllMovies(): PagingSource<Int, DiscoverMovieResultsItem>

    @Query("DELETE FROM discover_movies")
    suspend fun clearMovies()
}