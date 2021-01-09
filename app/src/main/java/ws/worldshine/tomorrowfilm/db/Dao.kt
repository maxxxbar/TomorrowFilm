package ws.worldshine.tomorrowfilm.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ws.worldshine.tomorrowfilm.model.DiscoverMovieResultsItem
import ws.worldshine.tomorrowfilm.model.FavoriteMovies

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<DiscoverMovieResultsItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: FavoriteMovies)

    @Query("SELECT * FROM discover_movies WHERE id =:id")
    fun getMovieById(id: Int): DiscoverMovieResultsItem?

    @Query("SELECT * FROM discover_movies WHERE id =:id")
    fun getMovieByIdAsFlow(id: Int): Flow<DiscoverMovieResultsItem>

    @Query("SELECT * FROM favorite_movies WHERE id =:id")
    suspend fun getFavoriteMovieById(id: Int): FavoriteMovies?

    @Query("SELECT * FROM favorite_movies WHERE id =:id")
    fun getFavoriteMovieByIdAsFLow(id: Int): Flow<FavoriteMovies>

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

    @Query("SELECT * FROM favorite_movies ORDER BY uniqueId ASC limit :startLimit,:endLimit")
    suspend fun getFavoriteMoviesByLimit(startLimit: Int, endLimit: Int): List<FavoriteMovies>

}