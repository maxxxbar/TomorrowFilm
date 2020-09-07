package com.example.mymovies.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import java.util.List;

public class MovieDataBaseViewModelNew extends AndroidViewModel {

    private Repository repository;
    private DataSource.Factory<Integer, List<MovieDB>> movies;
    private LiveData<List<FavoriteMovie>> favoriteMovies;

    public MovieDataBaseViewModelNew(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        movies = repository.getMoviesListLiveData();
        favoriteMovies = repository.getFavoriteMoviesListLiveData();
    }

    public DataSource.Factory<Integer, List<MovieDB>> getAllMovies() {
        return movies;
    }

    public LiveData<List<FavoriteMovie>> getAllFavoriteMovies() {
        return favoriteMovies;
    }

    public void insertMovie(MovieDB movie) {
        repository.insertMovie(movie);
    }

    public void deleteAllMovies() {
        repository.deleteAllMovies();
    }
}
