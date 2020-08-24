package com.example.mymovies.database;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import java.util.List;

public class Repository {
    private MovieDatabase database;
    private MovieDao movieDao;
    private DataSource.Factory<Integer, List<MovieDB>> moviesListLiveData;
    private LiveData<List<FavoriteMovie>> favoriteMoviesListLiveData;

    Repository(Application application) {
        database = MovieDatabase.getInstance(application);
        movieDao = database.movieDao();
        moviesListLiveData = movieDao.getAllMoviesNew();
        favoriteMoviesListLiveData = movieDao.getAllFavoriteMovie();
    }

    public DataSource.Factory<Integer, List<MovieDB>> getMoviesListLiveData() {
        return moviesListLiveData;
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMoviesListLiveData() {
        return favoriteMoviesListLiveData;
    }

    void insertMovie(MovieDB movie) {
        MovieDatabase.databaseWriteExecutor.execute(() -> {
            movieDao.insertMovie(movie);
        });
    }


    void deleteAllMovies() {
        MovieDatabase.databaseWriteExecutor.execute(() -> {
            movieDao.deleteAllMovies();
        });
    }
}
