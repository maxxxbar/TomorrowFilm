package com.example.mymovies.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymovies.model.DiscoverMovieResultsItem
import com.example.mymovies.model.FavoriteMovies
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<DiscoverMovieResultsItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: FavoriteMovies)

    @Query("SELECT * FROM discover_movies WHERE id =:id")
    fun getMovieById(id: Int): DiscoverMovieResultsItem

    @Query("SELECT * FROM favorite_movies WHERE id =:id")
    fun getFavoriteMovieById(id: Int): FavoriteMovies

    @Query("DELETE FROM favorite_movies WHERE id =:id")
    fun deleteFavoriteMovieById(id: Int)

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovies(): List<FavoriteMovies>

    @Query("SELECT * FROM discover_movies WHERE id =:id")
    fun getOneMovieAsLiveData(id: Int): Flow<DiscoverMovieResultsItem>

    @Query("SELECT * FROM discover_movies")
    fun getAllMovies(): PagingSource<Int, DiscoverMovieResultsItem>

    @Query("DELETE FROM discover_movies")
    suspend fun clearMovies()
}