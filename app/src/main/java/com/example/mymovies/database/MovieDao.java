package com.example.mymovies.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mymovies.etc.DataSource;

import java.util.List;


@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    LiveData<MovieDB> getAllMovies();

    @Query("SELECT * FROM movies")
    DataSource.Factory<Integer, MovieDB> getAllMoviesNew();

    @Query("SELECT * FROM favorite_movies")
    LiveData<List<FavoriteMovie>> getAllFavoriteMovie();

    @Query("SELECT * FROM movies WHERE id == :movieId")
    MovieDB getMovieById(int movieId);

    @Query("SELECT * FROM favorite_movies WHERE id == :movieId")
    FavoriteMovie getFavoriteMovieById(int movieId);

    @Query("DELETE FROM movies")
    void deleteAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieDB movie);

    @Delete
    void deleteMovie(MovieDB movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavoriteMovie(FavoriteMovie favoriteMovie);

    @Delete
    void deleteFavoriteMovie(FavoriteMovie favoriteMovie);

    @Query("SELECT id FROM favorite_movies WHERE id == :movieId")
    int getIdFromFavoriteMovie(int movieId);


}
